package server;

import config.ServiceLocator;
import controladores.*;
import io.github.flbulgarelli.jpa.extras.test.SimplePersistenceTest;
import io.javalin.Javalin;

public class Router implements SimplePersistenceTest {
    public void init(Javalin app) {
        app.get("/",(ctx)->{
            ctx.render("/index.hbs"); //Render la pagina principal
        });
        app.before(ctx -> {
            entityManager().clear();
        });
        app.post("/colaborador-fisico",ServiceLocator.instanceOf(ControladorColaboradorFisico.class)::save);
        app.post("/colaborador-juridico",ServiceLocator.instanceOf(ControladorColaboradorJuridico.class)::save);

        //Dashboard
        app.get("/dashboard",(ctx) ->{
            ctx.render("/dashboard.hbs");
        });
        app.get("/dashboard/fisicos",ServiceLocator.instanceOf(ControladorColaboradorFisico.class)::index);


        //dashboard/heladeras
        app.get("/dashboard/heladeras", ServiceLocator.instanceOf(ControladorHeladeras.class)::index);
        app.post("/dashboard/heladeras",ServiceLocator.instanceOf(ControladorHeladeras.class)::create);
        app.get("/dashboard/heladeras/{id}/edit",ServiceLocator.instanceOf(ControladorHeladeras.class)::edit);
        app.post("/dashboard/heladeras/{id}/edit",ServiceLocator.instanceOf(ControladorHeladeras.class)::update);
        app.post("/dashboard/heladeras/{id}/delete",ServiceLocator.instanceOf(ControladorHeladeras.class)::delete);

        //dashboard/tecnicos
        app.get("/dashboard/tecnicos", ServiceLocator.instanceOf(ControladorTecnicos.class)::index);
        app.post("/dashboard/tecnicos", ServiceLocator.instanceOf(ControladorTecnicos.class)::save);
        app.get("/dashboard/tecnicos/{id}/edit", ServiceLocator.instanceOf(ControladorTecnicos.class)::edit);
        app.post("/dashboard/tecnicos/{id}/edit", ServiceLocator.instanceOf(ControladorTecnicos.class)::update);
        app.get("/dashboard/tecnicos/{id}/delete", ServiceLocator.instanceOf(ControladorTecnicos.class)::delete);
        app.post("/dashboard/tecnicos/{id}/delete", ServiceLocator.instanceOf(ControladorTecnicos.class)::remove);

        //dashboard/suscripciones
        app.get("/dashboard/suscripciones", ServiceLocator.instanceOf(ControladorSuscripciones.class)::index);
        app.post("/dashboard/suscripciones", ServiceLocator.instanceOf(ControladorSuscripciones.class)::save);
        app.get("/dashboard/suscripciones/create", ServiceLocator.instanceOf(ControladorSuscripciones.class)::create);
        app.get("/dashboard/suscripciones/{id}/edit", ServiceLocator.instanceOf(ControladorSuscripciones.class)::edit);
        app.post("/dashboard/suscripciones/{id}/edit", ServiceLocator.instanceOf(ControladorSuscripciones.class)::update);
        app.get("/dashboard/suscripciones/{id}/delete", ServiceLocator.instanceOf(ControladorSuscripciones.class)::delete);
        app.post("/dashboard/suscripciones/{id}/delete", ServiceLocator.instanceOf(ControladorSuscripciones.class)::remove);
    }

}