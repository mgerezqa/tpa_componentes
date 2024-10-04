package server;

import config.ServiceLocator;
import controladores.ControladorColaboradorFisico;
import controladores.ControladorHeladeras;
import io.javalin.Javalin;

public class Router {
    public static void init(Javalin app) {
        app.get("/",(ctx)->{
            ctx.render("/index.hbs"); //Render la pagina principal
        });
        app.get("/heladeras", ServiceLocator.instanceOf(ControladorHeladeras.class)::index);

        //Endpoints de colaborador fisico
        app.post("/colaborador-fisico",ServiceLocator.instanceOf(ControladorColaboradorFisico.class)::save);

    }
}