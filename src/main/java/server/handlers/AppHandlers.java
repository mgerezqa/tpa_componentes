package server.handlers;

import io.javalin.Javalin;
import server.exceptions.TarjetasException;

public class AppHandlers {
    private IHandler[] handlers = new IHandler[]{
            new AccessDeniedHandler(),
            new TarjetasExceptionHandler(),
    };

    public static void applyHandlers(Javalin app) {
        AppHandlers appHandlers = new AppHandlers();
        for (IHandler handler : appHandlers.handlers) {
            handler.setHandle(app);
        }
    }
}