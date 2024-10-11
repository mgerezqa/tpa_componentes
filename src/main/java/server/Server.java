package server;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;
import com.github.jknack.handlebars.Template;
import domain.Config;
import domain.formulario.documentos.TipoDocumento;
import domain.geografia.area.TamanioArea;
import domain.heladera.Heladera.EstadoHeladera;
import domain.suscripciones.TipoDeSuscripcionENUM;
import utils.Initializer;
import utils.JavalinRenderer;
import io.javalin.Javalin;
import io.javalin.config.JavalinConfig;
import io.javalin.http.HttpStatus;

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
            Integer port = Integer.parseInt(Config.getInstance().getProperty("server_port"));
            app = Javalin.create(config()).start(port);

            new Router().init(app);

            if (Boolean.parseBoolean(Config.getInstance().getProperty("dev_mode"))) {
                Initializer.init();
            }
        }
    }

    private static Consumer<JavalinConfig> config() {
        return config -> {
            config.staticFiles.add(staticFiles -> {
                staticFiles.hostedPath = "/";
                staticFiles.directory = "public";
            });

            config.fileRenderer(new JavalinRenderer().register("hbs", (path, model, context) -> {
                Handlebars handlebars = new Handlebars();
                handlebars.registerHelper("eq", new Helper<Object>() {
                    @Override
                    public Object apply(Object context, Options options) {
                        if (context == null || options.param(0) == null) {
                            return false; // Manejo de nulos
                        }
                        return context.equals(options.param(0));
                    }
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
            config.validation.register(TipoDocumento.class,  v->  TipoDocumento.valueOf(v.toUpperCase()));
            config.validation.register(TamanioArea.class, v->  TamanioArea.valueOf(v.toUpperCase()));
            config.validation.register(EstadoHeladera.class, v->  EstadoHeladera.valueOf(v.toUpperCase()));
            config.validation.register(TipoDeSuscripcionENUM.class, v->  TipoDeSuscripcionENUM.valueOf(v.toUpperCase()));

        };
    }
}