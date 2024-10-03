package server;

import config.ServiceLocator;
import controladores.ControladorHeladeras;
import io.javalin.Javalin;

public class Router {

    public static void init(Javalin app) {
        //EJEMPLOS
        app.get("/prueba", ctx -> ctx.result("Hola mundo!"));

        //Query Params
        app.get("/saludo", ctx -> {
            ctx.result("Hola " + ctx.queryParam("nombre") + " " + ctx.queryParam("apellido"));
        });

        //Route params | Path params
        app.get("/saludo-para/{nombre}", ctx -> ctx.result("Hola " + ctx.pathParam("nombre")));

        app.get("/heladeras", ServiceLocator.instanceOf(ControladorHeladeras.class)::index);
    }
}