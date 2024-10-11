package controladores;

import io.javalin.http.Context;
import repositorios.repositoriosBDD.RepositorioSuscripciones;
import utils.ICrudViewsHandler;

public class ControladorSuscripciones implements ICrudViewsHandler {
    private RepositorioSuscripciones repositorioSuscripciones;

    public ControladorSuscripciones(RepositorioSuscripciones repositorioSuscripciones) {
        this.repositorioSuscripciones = repositorioSuscripciones;
    }

    @Override
    public void index(Context context) {
        context.render("/dashboard/suscripciones");
    }

    @Override
    public void show(Context context) {

    }

    @Override
    public void create(Context context) {

    }

    @Override
    public void save(Context context) {

    }

    @Override
    public void edit(Context context) {

    }

    @Override
    public void update(Context context) {

    }

    @Override
    public void delete(Context context) {

    }

    @Override
    public void remove(Context context) {

    }
}
