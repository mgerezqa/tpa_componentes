package controladores;


import domain.heladera.Heladera.Heladera;
import domain.suscripciones.*;
import domain.usuarios.Colaborador;
import domain.usuarios.ColaboradorFisico;
import dtos.SuscripcionDTO;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
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

public class ControladorSuscripciones implements ICrudViewsHandler, WithSimplePersistenceUnit {
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
        List<Long> colaboradoresPosibles = repositorioColaboradores.colaboradoreFisicosActivos().stream().map(Colaborador::getId).collect(Collectors.toList());
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
        Long idColab = Long.valueOf(context.formParam("idColaborador"));
        Long idHela = Long.valueOf(context.formParam("idHeladera"));
        Optional<Object> posibleColaborador = repositorioColaboradores.buscarPorID(Colaborador.class,idColab);
        Optional<Object> posibleHeladera = repositorioHeladeras.buscarPorID(Heladera.class,idHela);
        if(posibleColaborador.isEmpty()|| posibleHeladera.isEmpty()){
            System.out.println("NO existe la heladera o el colaborador!");
            context.redirect("/dashboard/suscripciones"); //ERROR
            return;
        }
        //Validaciones
        //Validator<Long> idColaborador = context.formParamAsClass("idColaborador", Long.class)
        //        .check(v -> repositorioColaboradores.buscarPorID(Colaborador.class,v).isPresent(), "El id de este colaborador no existe o no esta activo!");
        //Validator<Long> idHeladera = context.formParamAsClass("idHeladera", Long.class)
        //        .check(v -> repositorioHeladeras.buscarPorID(Heladera.class,v).isPresent(), "El id de esta heladera no existe o no esta activo!");
        Validator<TipoDeSuscripcionENUM> suscripcionTipo = context.formParamAsClass("suscripcionTipo", TipoDeSuscripcionENUM.class)
                .check(Objects::nonNull , "El tipo de documento es  obligatorio");
        boolean activo = context.formParam("estadoSuscripcion")!= null;

        //Errores
        Map<String, List<ValidationError<?>>> errors = Validation.collectErrors(suscripcionTipo);

        if(!errors.isEmpty()){
            System.out.println(errors);
            context.redirect("/dashboard/suscripciones"); // TODO -> Pantalla del form pero mencionando los errores al usuario
            return;
        }
        withTransaction(()->{
            TipoDeSuscripcion tipoDeSuscripcion = null;
            System.out.println(suscripcionTipo.get());
            if (suscripcionTipo.get() == TipoDeSuscripcionENUM.SUSCRIPCION_CANTIDAD_VIANDAS_MAX){
                tipoDeSuscripcion = new SuscripcionPorCantidadDeViandasHastaAlcMax();
            }
            if (suscripcionTipo.get() == TipoDeSuscripcionENUM.SUSCRIPCION_VIANDAS_DIS){
                tipoDeSuscripcion = new SuscripcionPorCantidadDeViandasDisponibles();
            }
            if (suscripcionTipo.get() == TipoDeSuscripcionENUM.SUSCRIPCION_DESPERFECTO_HELADERA){
                tipoDeSuscripcion = new SuscripcionPorDesperfectoH();
            }
            if (tipoDeSuscripcion != null) {
                Suscripcion suscripcion = new Suscripcion();
                suscripcion.setHeladera((Heladera) posibleHeladera.get());
                suscripcion.setColaboradorFisico((ColaboradorFisico) posibleColaborador.get());
                suscripcion.setTipoDeSuscripcion(tipoDeSuscripcion);
                repositorioSuscripciones.guardar(suscripcion);
            }
        });
        context.redirect("/dashboard/suscripciones"); //TODO realizar pantalla de exito de la creacion de suscripcion
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
