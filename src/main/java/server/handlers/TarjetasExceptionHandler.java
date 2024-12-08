package server.handlers;

import io.javalin.Javalin;
import io.javalin.http.HttpStatus;
import java.util.Map;
import server.exceptions.TarjetasException;

public class TarjetasExceptionHandler implements IHandler {
    @Override
    public void setHandle(Javalin app) {
        app.exception(TarjetasException.class, (e, context) -> {
            context.status(HttpStatus.INTERNAL_SERVER_ERROR);
            Map<String, String> model = Map.of("error", "Error en el sistema de tarjetas: " + e.getMessage());
            context.render("/dashboard/error.hbs", model);
        });
    }
} 