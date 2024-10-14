package controladores;

import io.javalin.http.Context;
import repositorios.repositoriosBDD.RepositorioUsuarios;
import utils.ICrudViewsHandler;

public class ControladorUsuario implements ICrudViewsHandler {
    private RepositorioUsuarios repositorioUsuarios;

    public ControladorUsuario(RepositorioUsuarios repositorioUsuarios) {
        this.repositorioUsuarios = repositorioUsuarios;
    }


    @Override
    public void show(Context context) {
        context.render("/dashboard/forms/ajustes.hbs");
    }


    @Override
    public void index(Context context) {

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
