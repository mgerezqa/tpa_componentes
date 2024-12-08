package controladores;

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
        List<Reporte> reportes = this.repositorioReportes.obtenerTodos();

        Map<String, Object> model = new HashMap<>();
        model.put("reportes", reportes);

        context.render("reportes/reportes.hbs", model);

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
