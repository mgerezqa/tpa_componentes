package server;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import domain.donaciones.Donacion;
import domain.excepciones.ExcepcionCanjePuntosInsuficientes;
import domain.heladera.Heladera.Heladera;
import domain.heladera.Heladera.ModeloDeHeladera;
import domain.puntos.Canje;
import domain.puntos.Oferta;
import domain.usuarios.Colaborador;
import domain.usuarios.ColaboradorFisico;
import domain.usuarios.RoleENUM;
import config.ServiceLocator;
import controladores.*;
import io.javalin.Javalin;
import io.github.flbulgarelli.jpa.extras.test.SimplePersistenceTest;
import io.javalin.http.UploadedFile;
import io.javalin.http.HttpStatus;
import mappers.HeladeraMapper;
import mappers.dtos.HeladeraDTO;
import repositorios.Repositorio;

import java.util.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class Router implements SimplePersistenceTest{

    public void init(Javalin app) {
        app.before(ctx -> {
            entityManager().clear();
        });

        //Render la pagina principal
        app.get("/",(ctx)->{
            Map<String, Object> model = new HashMap<>();
            List<String> roles = ctx.sessionAttribute("roles");

            boolean esAdmin = roles != null && roles.contains(RoleENUM.ADMIN.toString());

            boolean esTecnico = roles != null && roles.contains(RoleENUM.TECNICO.toString());
            boolean esFisico = roles != null && roles.contains(RoleENUM.FISICO.toString());
            boolean esJuridico = roles != null && roles.contains(RoleENUM.JURIDICO.toString());
            List<Heladera> heladeras = ServiceLocator.instanceOf(Repositorio.class)
                    .buscarTodos(Heladera.class)
                    .stream()
                    .map(m -> (Heladera) m)
                    .collect(Collectors.toList());

            List<HeladeraDTO> heladerasDTO = heladeras.stream()
                    .map(HeladeraMapper::toDTO)
                    .collect(Collectors.toList());

            model.put("heladeras", heladerasDTO);
            model.put("tecnico",esTecnico);
            model.put("fisico",esFisico);
            model.put("juridico",esJuridico);
            model.put("admin", esAdmin);
            ctx.render("/index.hbs", model);
        });

        //Dashboard
        app.get("/dashboard",(ctx) ->{
            ctx.render("/dashboard.hbs");
        },RoleENUM.ADMIN);

        //dashboard/ajustes
        app.get("/dashboard/ajustes",ServiceLocator.instanceOf(ControladorUsuario.class)::show, RoleENUM.ADMIN);

        //dashboard/heladeras
        app.get("/dashboard/heladeras", ServiceLocator.instanceOf(ControladorHeladeras.class)::index,RoleENUM.ADMIN);
        app.post("/dashboard/heladeras",ServiceLocator.instanceOf(ControladorHeladeras.class)::save,RoleENUM.ADMIN);
        app.get("/dashboard/heladeras/create", ServiceLocator.instanceOf(ControladorHeladeras.class)::create,RoleENUM.ADMIN);
        app.get("/dashboard/heladeras/{id}/edit",ServiceLocator.instanceOf(ControladorHeladeras.class)::edit,RoleENUM.ADMIN);
        app.post("/dashboard/heladeras/{id}/edit",ServiceLocator.instanceOf(ControladorHeladeras.class)::update,RoleENUM.ADMIN);
        app.get("/dashboard/heladeras/{id}/delete",ServiceLocator.instanceOf(ControladorHeladeras.class)::delete,RoleENUM.ADMIN);
        app.post("/dashboard/heladeras/{id}/delete",ServiceLocator.instanceOf(ControladorHeladeras.class)::remove,RoleENUM.ADMIN);

        //dashboard/fisicos
        app.get("/dashboard/fisicos",ServiceLocator.instanceOf(ControladorColaboradorFisico.class)::index,RoleENUM.ADMIN);
        app.post("/dashboard/fisicos",ServiceLocator.instanceOf(ControladorColaboradorFisico.class)::create,RoleENUM.ADMIN);
        app.get("/dashboard/fisicos/{id}/edit",ServiceLocator.instanceOf(ControladorColaboradorFisico.class)::edit,RoleENUM.ADMIN);
        app.post("/dashboard/fisicos/{id}/edit",ServiceLocator.instanceOf(ControladorColaboradorFisico.class)::update,RoleENUM.ADMIN);
        app.get("/dashboard/fisicos/{id}/delete",ServiceLocator.instanceOf(ControladorColaboradorFisico.class)::delete,RoleENUM.ADMIN);
        app.post("/dashboard/fisicos/{id}/delete", ServiceLocator.instanceOf(ControladorColaboradorFisico.class)::remove,RoleENUM.ADMIN); // baja logica
        //dashboard/juridicos
        app.get("/dashboard/juridicos",ServiceLocator.instanceOf(ControladorColaboradorJuridico.class)::index,RoleENUM.ADMIN);
        app.post("/dashboard/juridicos",ServiceLocator.instanceOf(ControladorColaboradorJuridico.class)::create,RoleENUM.ADMIN);
        app.get("/dashboard/juridicos/{id}/edit",ServiceLocator.instanceOf(ControladorColaboradorJuridico.class)::edit,RoleENUM.ADMIN);
        app.post("/dashboard/juridicos/{id}/edit",ServiceLocator.instanceOf(ControladorColaboradorJuridico.class)::update,RoleENUM.ADMIN);
        app.get("/dashboard/juridicos/{id}/delete",ServiceLocator.instanceOf(ControladorColaboradorJuridico.class)::delete,RoleENUM.ADMIN);
        app.post("/dashboard/juridicos/{id}/delete", ServiceLocator.instanceOf(ControladorColaboradorJuridico.class)::remove,RoleENUM.ADMIN); // baja logica

        //dashboard/beneficiarios
        app.get("/dashboard/beneficiarios",ServiceLocator.instanceOf(ControladorBeneficiarios.class)::index,RoleENUM.ADMIN);
        app.post("/dashboard/beneficiarios",ServiceLocator.instanceOf(ControladorBeneficiarios.class)::create,RoleENUM.ADMIN);
        app.get("/dashboard/beneficiarios/{id}/edit",ServiceLocator.instanceOf(ControladorBeneficiarios.class)::edit,RoleENUM.ADMIN);
        app.post("/dashboard/beneficiarios/{id}/edit",ServiceLocator.instanceOf(ControladorBeneficiarios.class)::update,RoleENUM.ADMIN);
        app.get("/dashboard/beneficiarios/{id}/delete",ServiceLocator.instanceOf(ControladorBeneficiarios.class)::delete,RoleENUM.ADMIN);
        app.post("/dashboard/beneficiarios/{id}/delete", ServiceLocator.instanceOf(ControladorBeneficiarios.class)::remove,RoleENUM.ADMIN); // baja logica

        //dashboard/tecnicos
        app.get("/dashboard/tecnicos", ServiceLocator.instanceOf(ControladorTecnicos.class)::index,RoleENUM.ADMIN);
        app.post("/dashboard/tecnicos", ServiceLocator.instanceOf(ControladorTecnicos.class)::save,RoleENUM.ADMIN);
        app.get("/dashboard/tecnicos/create", ServiceLocator.instanceOf(ControladorTecnicos.class)::create,RoleENUM.ADMIN);
        app.get("/dashboard/tecnicos/{id}/edit", ServiceLocator.instanceOf(ControladorTecnicos.class)::edit,RoleENUM.ADMIN);
        app.post("/dashboard/tecnicos/{id}/edit", ServiceLocator.instanceOf(ControladorTecnicos.class)::update,RoleENUM.ADMIN);
        app.get("/dashboard/tecnicos/{id}/delete", ServiceLocator.instanceOf(ControladorTecnicos.class)::delete,RoleENUM.ADMIN);
        app.post("/dashboard/tecnicos/{id}/delete", ServiceLocator.instanceOf(ControladorTecnicos.class)::remove,RoleENUM.ADMIN);

        //dashboard/suscripciones
        app.get("/dashboard/suscripciones", ServiceLocator.instanceOf(ControladorSuscripciones.class)::index,RoleENUM.ADMIN);
        app.post("/dashboard/suscripciones", ServiceLocator.instanceOf(ControladorSuscripciones.class)::save,RoleENUM.ADMIN);
        app.get("/dashboard/suscripciones/create", ServiceLocator.instanceOf(ControladorSuscripciones.class)::create,RoleENUM.ADMIN);
        app.get("/dashboard/suscripciones/{id}/edit", ServiceLocator.instanceOf(ControladorSuscripciones.class)::edit,RoleENUM.ADMIN);
        app.post("/dashboard/suscripciones/{id}/edit", ServiceLocator.instanceOf(ControladorSuscripciones.class)::update,RoleENUM.ADMIN);
        app.get("/dashboard/suscripciones/{id}/delete", ServiceLocator.instanceOf(ControladorSuscripciones.class)::delete,RoleENUM.ADMIN);
        app.post("/dashboard/suscripciones/{id}/delete", ServiceLocator.instanceOf(ControladorSuscripciones.class)::remove,RoleENUM.ADMIN);

        //dashboard/tarjetas
        app.get("/dashboard/tarjetas", ServiceLocator.instanceOf(ControladorTarjetas.class)::index,RoleENUM.ADMIN);
        app.post("/dashboard/tarjetas", ServiceLocator.instanceOf(ControladorTarjetas.class)::save,RoleENUM.ADMIN);
        app.get("/dashboard/tarjetas/create", ServiceLocator.instanceOf(ControladorTarjetas.class)::create,RoleENUM.ADMIN);
        app.get("/dashboard/tarjetas/{id}/edit", ServiceLocator.instanceOf(ControladorTarjetas.class)::edit,RoleENUM.ADMIN);
        app.post("/dashboard/tarjetas/{id}/edit", ServiceLocator.instanceOf(ControladorTarjetas.class)::update,RoleENUM.ADMIN);
        app.get("/dashboard/tarjetas/{id}/delete", ServiceLocator.instanceOf(ControladorTarjetas.class)::delete,RoleENUM.ADMIN);
        app.post("/dashboard/tarjetas/{id}/delete", ServiceLocator.instanceOf(ControladorTarjetas.class)::remove,RoleENUM.ADMIN);


        //dashboard/donaciones
        app.get("/dashboard/donaciones/dinero", ServiceLocator.instanceOf(ControladorDonacionDinero.class)::index,RoleENUM.ADMIN);


        // // //

        // Ruta para carga masiva de donaciones
        app.post("/dashboard/carga-masiva", ctx -> {
            try {
                // Obtener el archivo subido
                UploadedFile uploadedFile = ctx.uploadedFile("file");

                if (uploadedFile == null) {
                    ctx.status(400).result("No se ha subido ningún archivo.");
                    return;
                }

                // Crear archivo temporal con el nombre del archivo subido
                java.nio.file.Path tempDir = java.nio.file.Files.createTempDirectory("uploads");
                File archivo = new File(tempDir.toFile(), uploadedFile.filename()); // Aquí se usa 'uploadedFile.filename'

                try (InputStream inputStream = uploadedFile.content();
                     FileOutputStream outputStream = new FileOutputStream(archivo)) {

                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                }

                // Obtener colaboradores existentes
                List<ColaboradorFisico> colaboradores = ServiceLocator.instanceOf(Repositorio.class)
                        .buscarTodos(ColaboradorFisico.class)
                        .stream()
                        .map(c -> (ColaboradorFisico) c)
                        .collect(Collectors.toList());

                // Instancia del controlador
                ControladorCargaMasiva controladorCargaMasiva = ServiceLocator.instanceOf(ControladorCargaMasiva.class);

                // Procesar el archivo
                String resultado = controladorCargaMasiva.procesarCargaMasiva(archivo, colaboradores);

                // Eliminar archivo temporal
                archivo.delete();
                tempDir.toFile().delete(); // Elimina también el directorio temporal

                // Devolver resultado
                ctx.result(resultado);
            } catch (Exception e) {
                e.printStackTrace();
                ctx.status(500).result("Error al procesar el archivo: " + e.getMessage());
            }
        }, RoleENUM.ADMIN);

        // // //


        //signup de colaborador fisico
        app.post("/fisico/signup", ServiceLocator.instanceOf(ControladorColaboradorFisico.class)::signup);
        app.post("/juridico/signup", ServiceLocator.instanceOf(ControladorColaboradorJuridico.class)::signup);


        // Login con usuario
        app.post("/login",ServiceLocator.instanceOf(ControladorUsuario.class)::login);

        // Cerrar sesion
        app.post("/logout", ctx ->{
            ctx.req().getSession().invalidate();
            ctx.redirect("/");
        });

        // FISICO/JURIDICO/TECNICO
        app.get("/home",ServiceLocator.instanceOf(ControladorUsuario.class)::home,RoleENUM.TECNICO,RoleENUM.FISICO,RoleENUM.JURIDICO);
        app.get("/estaciones", (ctx) -> {
            Map<String, Object> model = new HashMap<>();
            List<String> roles = ctx.sessionAttribute("roles");

            boolean esTecnico = roles.contains(RoleENUM.TECNICO.toString());
            boolean esFisico = roles.contains(RoleENUM.FISICO.toString());
            boolean esJuridico = roles.contains(RoleENUM.JURIDICO.toString());

            model.put("tecnico", esTecnico);
            model.put("fisico", esFisico);
            model.put("juridico", esJuridico);

            if (esJuridico) {
                List<ModeloDeHeladera> modelosHeladeras = ServiceLocator.instanceOf(Repositorio.class)
                    .buscarTodos(ModeloDeHeladera.class)
                    .stream()
                    .map(m -> (ModeloDeHeladera) m)
                    .collect(Collectors.toList());

                model.put("modelosHeladeras", modelosHeladeras);
            }
            List<Heladera> heladeras = ServiceLocator.instanceOf(Repositorio.class)
                    .buscarTodos(Heladera.class)
                    .stream()
                    .map(m -> (Heladera) m)
                    .collect(Collectors.toList());

            List<HeladeraDTO> heladerasDTO = heladeras.stream()
                    .map(HeladeraMapper::toDTO)
                    .collect(Collectors.toList());

            model.put("heladeras", heladerasDTO);

            ctx.render("home/estaciones/mapa.hbs", model);
        }, RoleENUM.TECNICO, RoleENUM.FISICO, RoleENUM.JURIDICO);
        app.get("/profile", ServiceLocator.instanceOf(ControladorUsuario.class)::perfil,RoleENUM.JURIDICO,RoleENUM.FISICO,RoleENUM.TECNICO);
        //TECNICOS
        app.post("/profile", ServiceLocator.instanceOf(ControladorTecnicos.class)::actualizar);
        app.get("/notificaciones",ServiceLocator.instanceOf(ControladorTecnicos.class)::notificaciones,RoleENUM.TECNICO);
        app.get("/visitas",ServiceLocator.instanceOf(ControladorTecnicos.class)::visitas,RoleENUM.TECNICO);
        //JURIDICO
        app.get("/mis-estaciones", ServiceLocator.instanceOf(ControladorColaboradorJuridico.class)::misEstaciones,RoleENUM.JURIDICO);
        app.post("/mantenerHeladera", ServiceLocator.instanceOf(ControladorColaboradorJuridico.class)::mantenerHeladera,RoleENUM.JURIDICO);
        app.post("/ofrecer-prod-servicio", ServiceLocator.instanceOf(ControladorColaboradorJuridico.class)::ofrecerOferta,RoleENUM.JURIDICO);

        //FISICOS
        app.post("/registrarVulnerable",ServiceLocator.instanceOf(ControladorColaboradorFisico.class)::registroPersonaVulnerable,RoleENUM.FISICO);
        app.post("/distribuir-viandas",ServiceLocator.instanceOf(ControladorColaboradorFisico.class)::distrubuirViandas,RoleENUM.FISICO);
        app.post("/donar-viandas",ServiceLocator.instanceOf(ControladorColaboradorFisico.class)::donarViandas,RoleENUM.FISICO);
        app.get("/recomendacion-comunidades",ServiceLocator.instanceOf(ControladorColaboradorFisico.class)::recomendarComunidades,RoleENUM.FISICO);

        //FISICO E JURIDICO
        app.post("/donar-dinero",ServiceLocator.instanceOf(ControladorColaboradorFisico.class)::donacionDinero,RoleENUM.FISICO,RoleENUM.JURIDICO); //La logica es la misma para el juridico, da cosa que este en el controlador del fisico.
        app.get("/donaciones", (ctx) -> {
            Map<String, Object> model = new HashMap<>();
            List<String> roles = ctx.sessionAttribute("roles");

            boolean esFisico = roles.contains(RoleENUM.FISICO.toString());
            boolean esJuridico = roles.contains(RoleENUM.JURIDICO.toString());

            Long idColaborador = ctx.sessionAttribute("id_colaborador");
            Optional<Object> colaborador = ServiceLocator.instanceOf(Repositorio.class)
                    .buscarPorID(Colaborador.class, idColaborador);
            if(esFisico){
                List<Heladera> heladeras = ServiceLocator.instanceOf(Repositorio.class)
                        .buscarTodos(Heladera.class)
                        .stream()
                        .map(m -> (Heladera) m)
                        .collect(Collectors.toList());

                List<HeladeraDTO> heladerasDTO = heladeras.stream()
                        .map(HeladeraMapper::toDTO)
                        .collect(Collectors.toList());
                System.out.println(heladerasDTO);
                model.put("heladeras", heladerasDTO);
            }

            List<Donacion> donaciones = ServiceLocator.instanceOf(Repositorio.class)
                    .buscarTodos(Donacion.class)
                    .stream().map(d -> (Donacion) d)
                    .filter(d -> d.getColaboradorQueLaDono().equals(colaborador.get()))
                    .sorted(Comparator.comparing(Donacion::getId))
                    .collect(Collectors.toList());

            model.put("donaciones", donaciones);
            model.put("fisico", esFisico);
            model.put("juridico", esJuridico);

            ctx.render("/home/donaciones/donaciones.hbs", model);
        }, RoleENUM.JURIDICO, RoleENUM.FISICO);
        app.get("/puntos", (ctx) -> {
            Map<String, Object> model = new HashMap<>();

            Long idColaborador = ctx.sessionAttribute("id_colaborador");
            Optional<Object> colaborador = ServiceLocator.instanceOf(Repositorio.class)
                    .buscarPorID(Colaborador.class, idColaborador);

            List<Donacion> donaciones = ServiceLocator.instanceOf(Repositorio.class)
                    .buscarTodos(Donacion.class)
                    .stream().map(d -> (Donacion) d)
                    .filter(Donacion::getCompletado)
                    .filter(d -> d.getColaboradorQueLaDono().equals(colaborador.get()))
                    .sorted(Comparator.comparing(Donacion::getId))
                    .collect(Collectors.toList());

            model.put("donaciones", donaciones);
            model.put("colaborador", colaborador.get());
            ctx.render("/home/puntos/puntos.hbs", model);
        }, RoleENUM.JURIDICO, RoleENUM.FISICO);
        app.get("/canjes", (ctx) -> {
            Map<String, Object> model = new HashMap<>();
            Repositorio repositorio = ServiceLocator.instanceOf(Repositorio.class);
            List<Oferta> ofertasDisponibles = repositorio.buscarTodos(Oferta.class)
                    .stream()
                    .map(m -> (Oferta) m)
                    .collect(Collectors.toList());
            Long idColaborador = ctx.sessionAttribute("id_colaborador");
            Optional<Object> colaborador = repositorio
                    .buscarPorID(Colaborador.class, idColaborador);
            Colaborador posibleComprador = (Colaborador) colaborador.get();

            List<Canje> canjesDelColaborador = repositorio.buscarTodos(Canje.class)
                    .stream()
                    .map(m -> (Canje) m)
                    .filter(c -> c.getCanjeador().getId() == posibleComprador.getId())
                    .collect(Collectors.toList());

            model.put("canjes",canjesDelColaborador);
            model.put("colaborador",posibleComprador);
            model.put("ofertas",ofertasDisponibles);
            ctx.render("/home/canjes/canjes.hbs", model);
        }, RoleENUM.JURIDICO, RoleENUM.FISICO);

        app.post("/canjear", (context) -> {
            Repositorio repositorio =ServiceLocator.instanceOf(Repositorio.class);
            Long idOfertaElegida = Long.valueOf(Objects.requireNonNull(context.formParam("campo_tipo_canje_fisico")));
            Optional<Object> posibleOferta = repositorio.buscarPorID(Oferta.class,idOfertaElegida);
            if(posibleOferta.isPresent()){
                Oferta ofertaElegida = (Oferta) posibleOferta.get();
                Long idColaborador = context.sessionAttribute("id_colaborador");
                Optional<Object> colaborador = repositorio
                        .buscarPorID(Colaborador.class, idColaborador);
                Colaborador comprador = (Colaborador) colaborador.get();
                //Hacer el canje
                try {
                    ofertaElegida.hacerCanje(comprador,ofertaElegida);
                    //Tengo que crear la nueva entidad "compra/factura" para despues mostrarles en sus canjes.
                    Integer costoPuntos = ofertaElegida.getCostoPuntos(); //Se desnormalizo para consistencia de datos, y poder evitar que se tenga un precio antiguo debido a que puede cambiar la oferta
                    Canje canje = new Canje(comprador,ofertaElegida,costoPuntos);
                    withTransaction(()->{
                        //Con el efecto cascada se hace la actualización de las demas entidades.
                        repositorio.guardar(canje);
                    });
                }catch (ExcepcionCanjePuntosInsuficientes exception){
                    context.redirect("/canjes");
                }
                context.redirect("/canjes");
            }else{
                context.status(HttpStatus.NOT_FOUND);
            }
        }, RoleENUM.JURIDICO, RoleENUM.FISICO);

    }
}