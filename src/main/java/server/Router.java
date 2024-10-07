package server;

import config.ServiceLocator;
import controladores.ControladorColaboradorFisico;
import controladores.ControladorColaboradorJuridico;
import controladores.ControladorHeladeras;
import io.javalin.Javalin;

public class Router {
    public static void init(Javalin app) {
        app.get("/",(ctx)->{
            ctx.render("/index.hbs"); //Render la pagina principal
        });
        app.get("/dashboard",(ctx) ->{
            ctx.render("/dashboard.hbs");
        });
        app.get("/heladeras", ServiceLocator.instanceOf(ControladorHeladeras.class)::index); //Ejemplo ruta con las heladeras de la base de datos
        //Endpoints de colaborador fisico, ejemplos
        app.post("/colaborador-fisico",ServiceLocator.instanceOf(ControladorColaboradorFisico.class)::save);
        app.post("/colaborador-juridico",ServiceLocator.instanceOf(ControladorColaboradorJuridico.class)::save);
    }
}