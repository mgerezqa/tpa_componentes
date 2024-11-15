package server;

import domain.heladera.Heladera.Heladera;
import domain.heladera.Heladera.ModeloDeHeladera;
import domain.usuarios.RoleENUM;
import config.ServiceLocator;
import controladores.*;
import io.javalin.Javalin;
import io.github.flbulgarelli.jpa.extras.test.SimplePersistenceTest;
import mappers.HeladeraMapper;
import mappers.dtos.HeladeraDTO;
import repositorios.Repositorio;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Router implements SimplePersistenceTest{

    public void init(Javalin app) {
        app.before(ctx -> {
            entityManager().clear();
        });

        //Testing sesiones basica
        app.get("/guardar-en-sesion", ctx -> {
            ctx.sessionAttribute("nombre", ctx.queryParam("nombre"));
            ctx.result("Variable de sesion guardada");
        });

        app.get("/saludo-sesionado", ctx -> ctx.result("Hola " + ctx.sessionAttribute("nombre")));

        //Render la pagina principal
        app.get("/",(ctx)->{
            Map<String, Object> model = new HashMap<>();
            List<String> roles = ctx.sessionAttribute("roles");

            boolean esAdmin = roles != null && roles.contains(RoleENUM.ADMIN.toString());

            boolean esTecnico = roles != null && roles.contains(RoleENUM.TECNICO.toString());
            boolean esFisico = roles != null && roles.contains(RoleENUM.FISICO.toString());
            boolean esJuridico = roles != null && roles.contains(RoleENUM.JURIDICO.toString());

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

        // Creación de usuario ADMIN
        app.get("/crear-admin", ServiceLocator.instanceOf(ControladorUsuario.class)::create);

        //Rutas del dashboard de los colaboradores,tecnicos

        // /Home -> Dashboard de colaboradores, tecnicos
        app.get("/home",ServiceLocator.instanceOf(ControladorUsuario.class)::home,RoleENUM.TECNICO,RoleENUM.FISICO,RoleENUM.JURIDICO);

        // /profile
        app.get("/profile", ServiceLocator.instanceOf(ControladorUsuario.class)::perfil,RoleENUM.JURIDICO,RoleENUM.FISICO,RoleENUM.TECNICO);
        app.post("/profile", ServiceLocator.instanceOf(ControladorTecnicos.class)::actualizar);
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
            if (esTecnico || esFisico) {
                List<Heladera> heladeras = ServiceLocator.instanceOf(Repositorio.class)
                        .buscarTodos(Heladera.class)
                        .stream()
                        .map(m -> (Heladera) m)
                        .collect(Collectors.toList());

                List<HeladeraDTO> heladerasDTO = heladeras.stream()
                        .map(HeladeraMapper::toDTO)
                        .collect(Collectors.toList());

                model.put("heladeras", heladerasDTO);
            }

            ctx.render("home/estaciones/mapa.hbs", model);
        }, RoleENUM.TECNICO, RoleENUM.FISICO, RoleENUM.JURIDICO);
        app.get("/donaciones", (ctx) ->{
           ctx.render("/home/donaciones/donaciones.hbs");
        },RoleENUM.JURIDICO,RoleENUM.FISICO);
        app.post("/mantenerHeladera", ServiceLocator.instanceOf(ControladorColaboradorJuridico.class)::mantenerHeladera,RoleENUM.JURIDICO);

    }
}