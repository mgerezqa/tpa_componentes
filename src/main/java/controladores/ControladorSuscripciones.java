package controladores;

import domain.suscripciones.Suscripcion;
import domain.usuarios.Tecnico;
import dtos.SuscripcionDTO;
import dtos.TecnicoDTO;
import io.javalin.http.Context;
import repositorios.repositoriosBDD.RepositorioSuscripciones;
import utils.ICrudViewsHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControladorSuscripciones implements ICrudViewsHandler {
    private RepositorioSuscripciones repositorioSuscripciones;

    public ControladorSuscripciones(RepositorioSuscripciones repositorioSuscripciones) {
        this.repositorioSuscripciones = repositorioSuscripciones;
    }

    @Override
    public void index(Context context) {
        List<Suscripcion> suscripciones = repositorioSuscripciones.buscarTodosSuscripciones();
        List<SuscripcionDTO> suscripcionesDTO = new ArrayList<>();
        for (Suscripcion suscripcion : suscripciones) {
            try {
                SuscripcionDTO suscripcionDTO = new SuscripcionDTO();
                suscripcionDTO.setId(suscripcion.getId());

                suscripcionesDTO.add(suscripcionDTO);
            }catch (Exception ex){
                System.out.println(ex.getMessage());
            }
        }
        Map<String,List<SuscripcionDTO>> model = new HashMap<>();
        model.put("suscripciones",suscripcionesDTO);
        context.render("/dashboard/suscripciones.hbs",model);
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
