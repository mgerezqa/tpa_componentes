package controladores;

import config.ServiceLocator;
import domain.reportes.Reporte;
import io.javalin.http.Context;
import repositorios.repositoriosBDD.RepositorioReportes;
import utils.ICrudViewsHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControladorReportes implements ICrudViewsHandler {
    private RepositorioReportes repositorioReportes;

    public ControladorReportes(RepositorioReportes repositorioReportes) {
        this.repositorioReportes = repositorioReportes;
    }

    @Override
    public void index(Context context) {

        context.render("/home/reportes/reportes.hbs");
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
