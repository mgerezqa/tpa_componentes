package server;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import config.ServiceLocator;
import domain.Config;
import domain.excepciones.CuilInvalidoException;
import domain.formulario.documentos.Cuil;
import domain.formulario.documentos.TipoDocumento;
import domain.geografia.area.TamanioArea;
import domain.heladera.Heladera.EstadoHeladera;
import domain.suscripciones.TipoDeSuscripcionENUM;
import domain.usuarios.Rubro;
import domain.usuarios.TipoRazonSocial;
import io.javalin.http.staticfiles.Location;
import middlewares.AuthMiddleware;
import repositorios.Repositorio;
import repositorios.repositoriosBDD.RepositorioColaboradores;
import repositorios.repositoriosBDD.RepositorioRoles;
import repositorios.repositoriosBDD.RepositorioTecnicos;
import repositorios.repositoriosBDD.RepositorioUsuarios;
import server.exceptions.CustomEnumConversionException;
import server.handlers.AppHandlers;
import utils.Broker.ServiceBroker;
import utils.Initializer;
import utils.JavalinRenderer;
import io.javalin.Javalin;
import io.javalin.config.JavalinConfig;
import io.javalin.http.HttpStatus;

import javax.xml.bind.ValidationException;
import java.io.IOException;
import java.util.function.Consumer;

public class Server {
    private static Javalin app = null;

    public static Javalin app() {
        if (app == null)
            throw new RuntimeException("App no inicializada");
        return app;
    }

    public static void init() {
        if (app == null) {
            int port = Integer.parseInt(Config.getInstance().getProperty("server_port"));
            app = Javalin.create(config()).start(port);
            new AuthMiddleware(ServiceLocator.instanceOf(RepositorioRoles.class)).apply(app);
            AppHandlers.applyHandlers(app);
            new Router().init(app);
            ServiceLocator.instanceOf(ServiceBroker.class);
            if (Boolean.parseBoolean(Config.getInstance().getProperty("dev_mode"))) {
                Initializer initializer = new Initializer(ServiceLocator.instanceOf(RepositorioRoles.class),ServiceLocator.instanceOf(RepositorioUsuarios.class),ServiceLocator.instanceOf(RepositorioColaboradores.class),ServiceLocator.instanceOf(RepositorioTecnicos.class),ServiceLocator.instanceOf(Repositorio.class));
                initializer.init();
            }
        }
    }

    private static Consumer<JavalinConfig> config() {
        return config -> {
            config.staticFiles.add(staticFiles -> {
                staticFiles.hostedPath = "/";
                staticFiles.directory = "public";
            });
            // Nueva configuración para el directorio de uploads
            config.staticFiles.add(staticFiles -> {
                staticFiles.hostedPath = "/uploads";  // Ruta URL base para acceder a las imágenes
                staticFiles.directory = "uploads";     // Directorio físico donde se guardan las imágenes
                staticFiles.location = Location.EXTERNAL;
            });

            config.fileRenderer(new JavalinRenderer().register("hbs", (path, model, context) -> {
                Handlebars handlebars = new Handlebars();
                handlebars.registerHelper("eq", (context1, options) -> {
                    if (context1 == null || options.param(0) == null) {
                        return false;
                    }
                    return context1.equals(options.param(0));
                });


                Template template = null;
                try {
                    template = handlebars.compile(
                            "templates/" + path.replace(".hbs", ""));
                    return template.apply(model);
                } catch (IOException e) {
                    e.printStackTrace();
                    context.status(HttpStatus.NOT_FOUND);
                    return "No se encuentra la página indicada...";
                }
            }));
            config.validation.register(TipoDocumento.class,  v->  {
                try {
                    return TipoDocumento.valueOf(v.toUpperCase());
                } catch (IllegalArgumentException e) {
                    throw new CustomEnumConversionException("El valor introducido no es un valor valido!", e);
                }
            });
            config.validation.register(TamanioArea.class, v->  TamanioArea.valueOf(v.toUpperCase()));
            config.validation.register(EstadoHeladera.class, v->  EstadoHeladera.valueOf(v.toUpperCase()));
            config.validation.register(TipoDeSuscripcionENUM.class, v->  TipoDeSuscripcionENUM.valueOf(v.toUpperCase()));
            config.validation.register(TipoRazonSocial.class, v->  TipoRazonSocial.valueOf(v.toUpperCase()));
            config.validation.register(Rubro.class, v->  Rubro.valueOf(v.toUpperCase()));

            // Habilitar CORS
            config.bundledPlugins.enableCors(cors -> {
                cors.addRule(it -> {
                    it.allowHost("http://localhost:8081");
                    it.allowHost("http://localhost:3000");
                    it.anyHost();
                    it.allowCredentials = true;
                });
            });


        };


    }
}