package server;
import config.ServiceLocator;
import controladores.ControladorColaboradorFisico;
import controladores.ControladorColaboradorJuridico;
import controladores.ControladorHeladeras;
import io.javalin.Javalin;
import io.github.flbulgarelli.jpa.extras.test.SimplePersistenceTest;

public class Router implements SimplePersistenceTest{

    public void init(Javalin app) {

        //Render la pagina principal
        app.get("/",(ctx)->{
            ctx.render("/index.hbs");
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

        //dashboard/heladeras
        app.get("/dashboard/heladeras", ServiceLocator.instanceOf(ControladorHeladeras.class)::index);
        app.post("/dashboard/heladeras",ServiceLocator.instanceOf(ControladorHeladeras.class)::create);
        app.get("/dashboard/heladeras/{id}/edit",ServiceLocator.instanceOf(ControladorHeladeras.class)::edit);
        app.post("/dashboard/heladeras/{id}/edit",ServiceLocator.instanceOf(ControladorHeladeras.class)::update);
        app.post("/dashboard/heladeras/{id}/delete",ServiceLocator.instanceOf(ControladorHeladeras.class)::delete);

        //dashboard/fisicos
        app.get("/dashboard/fisicos",ServiceLocator.instanceOf(ControladorColaboradorFisico.class)::index);
        app.post("/dashboard/fisicos",ServiceLocator.instanceOf(ControladorColaboradorFisico.class)::create);
        app.get("/dashboard/fisicos/{id}/edit",ServiceLocator.instanceOf(ControladorColaboradorFisico.class)::edit);
        app.post("/dashboard/fisicos/{id}/edit",ServiceLocator.instanceOf(ControladorColaboradorFisico.class)::update);
        app.post("/dashboard/fisicos/{id}/delete",ServiceLocator.instanceOf(ControladorColaboradorFisico.class)::delete);

        //dashboard/juridicos
        app.get("/dashboard/juridicos",ServiceLocator.instanceOf(ControladorColaboradorJuridico.class)::index);
        app.post("/dashboard/juridicos",ServiceLocator.instanceOf(ControladorColaboradorJuridico.class)::create);
        app.get("/dashboard/juridicos/{id}/edit",ServiceLocator.instanceOf(ControladorColaboradorJuridico.class)::edit);
        app.post("/dashboard/juridicos/{id}/edit",ServiceLocator.instanceOf(ControladorColaboradorJuridico.class)::update);
        app.post("/dashboard/juridicos/{id}/delete",ServiceLocator.instanceOf(ControladorColaboradorJuridico.class)::delete);
    }
}