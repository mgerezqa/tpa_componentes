package controladores;


import repositorios.repositoriosBDD.RepositorioColaboradores;
import utils.ICrudViewsHandler;
import io.javalin.http.Context;

public class ControladorColaboradorFisico implements ICrudViewsHandler {
    private RepositorioColaboradores repositorioColaboradores;

    public ControladorColaboradorFisico(RepositorioColaboradores repositorioColaboradores) {
        this.repositorioColaboradores = repositorioColaboradores;
    }
    @Override
    public void index(Context context){
        //TODO metodo para renderizar todos los colaboradores
    }
    @Override
    public void create(Context context) {

    }
    @Override
    public void save(Context context){
    }
    @Override
    public void show(Context context) {
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
}
