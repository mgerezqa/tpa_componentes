package controladores;

import domain.formulario.documentos.Cuil;
import domain.formulario.documentos.Documento;
import domain.formulario.documentos.TipoDocumento;
import domain.geografia.Calle;
import domain.geografia.Ubicacion;
import domain.geografia.area.AreaDeCobertura;
import domain.geografia.area.TamanioArea;
import domain.heladera.Heladera.Heladera;
import domain.suscripciones.Suscripcion;
import domain.usuarios.Colaborador;
import domain.usuarios.Tecnico;
import dtos.SuscripcionDTO;
import dtos.TecnicoDTO;
import io.javalin.http.Context;
import io.javalin.validation.Validation;
import io.javalin.validation.ValidationError;
import io.javalin.validation.Validator;
import repositorios.repositoriosBDD.RepositorioColaboradores;
import repositorios.repositoriosBDD.RepositorioHeladeras;
import repositorios.repositoriosBDD.RepositorioSuscripciones;
import utils.ICrudViewsHandler;

import java.util.*;
import java.util.stream.Collectors;

public class ControladorSuscripciones implements ICrudViewsHandler {
    private RepositorioSuscripciones repositorioSuscripciones;
    private RepositorioColaboradores repositorioColaboradores;
    private RepositorioHeladeras repositorioHeladeras;

    public ControladorSuscripciones(RepositorioSuscripciones repositorioSuscripciones, RepositorioColaboradores repositorioColaboradores, RepositorioHeladeras repositorioHeladeras) {
        this.repositorioSuscripciones = repositorioSuscripciones;
        this.repositorioColaboradores = repositorioColaboradores;
        this.repositorioHeladeras = repositorioHeladeras;
    }

    @Override
    public void index(Context context) {
        List<Suscripcion> suscripciones = repositorioSuscripciones.buscarTodosSuscripciones();
        List<SuscripcionDTO> suscripcionesDTO = new ArrayList<>();
        for (Suscripcion suscripcion : suscripciones) {
            try {
                SuscripcionDTO suscripcionDTO = new SuscripcionDTO();
                suscripcionDTO.setId(suscripcion.getId());
                suscripcionDTO.setTipoDeSuscripcion(suscripcion.getTipoDeSuscripcion().getDescripcion());
                suscripcionDTO.setIdColaborador(suscripcion.getColaboradorFisico().getId());
                suscripcionDTO.setIdHeladera(suscripcion.getHeladera().getId());
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
        List<Long> colaboradoresPosibles = repositorioColaboradores.colaboradoresActivos().stream().map(Colaborador::getId).collect(Collectors.toList());
        List<Long> heladerasPosibles = repositorioHeladeras.obtenerTodasLasHeladeras().stream().map(Heladera::getId).collect(Collectors.toList());
        Map<String,Object> modal = new HashMap<>();
        modal.put("colaboradores",colaboradoresPosibles);
        modal.put("heladeras",heladerasPosibles);
        modal.put("action","/dashboard/suscripciones");
        modal.put("edit",false);
        context.render("/dashboard/forms/suscripcion.hbs",modal);
    }

    @Override
    public void save(Context context) {

    }

    @Override
    public void edit(Context context) {
        String idParam = context.pathParam("id");
        Map<String,Object> modal = new HashMap<>();
        modal.put("action","/dashboard/suscripciones/"+idParam+"/edit");
        modal.put("edit",true);
        context.render("/dashboard/forms/suscripcion.hbs",modal);
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
