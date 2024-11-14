package server.handlers;

import domain.excepciones.CuilInvalidoException;
import io.javalin.Javalin;

public class CuilInvalidoHandler implements IHandler {

    @Override
    public void setHandle(Javalin app) {
        app.exception(CuilInvalidoException.class, (e, ctx) -> {
            ctx.result(e.getMessage()); //Provisional, hasta tener una estructura mejor para las excepciones
        });
    }
}
