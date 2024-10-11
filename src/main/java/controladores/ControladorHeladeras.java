package controladores;

import domain.geografia.Calle;
import domain.geografia.Provincia;
import domain.geografia.Ubicacion;
import domain.heladera.Heladera.EstadoHeladera;
import domain.heladera.Heladera.Heladera;
import domain.heladera.Heladera.ModeloDeHeladera;
import dtos.HeladeraDTO;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import io.javalin.validation.Validation;
import io.javalin.validation.ValidationError;
import io.javalin.validation.Validator;
import repositorios.repositoriosBDD.RepositorioHeladeras;
import utils.ICrudViewsHandler;

import java.time.LocalDate;
import java.util.*;

public class ControladorHeladeras implements ICrudViewsHandler, WithSimplePersistenceUnit {
    RepositorioHeladeras repositorioHeladeras;

    public ControladorHeladeras(RepositorioHeladeras repositorioHeladeras) {
        this.repositorioHeladeras = repositorioHeladeras;
    }
    @Override
    public void index(Context context) {
        List<Heladera> heladeras = repositorioHeladeras.obtenerTodasLasHeladeras();
        List<HeladeraDTO> heladerasDTO = new ArrayList<>();
        for (Heladera heladera : heladeras) {
            HeladeraDTO heladeraDTO = new HeladeraDTO();
            heladeraDTO.setId(heladera.getId());
            heladeraDTO.setNombreIdentificador(heladera.getNombreIdentificador());
            heladeraDTO.setEstadoHeladera(heladera.getEstadoHeladera());
            heladeraDTO.setCapacidadMax(heladera.getCapacidadMax());
            heladeraDTO.setCapacidadActual(heladera.getCapacidadActual());
            if(heladera.getFechaInicioFuncionamiento()!=null){
                heladeraDTO.setFechaInicioFuncionamiento(heladera.getFechaInicioFuncionamiento().toString());
            }
            heladeraDTO.setModeloDeHeladera(heladera.getModelo().getNombreModelo());
            heladerasDTO.add(heladeraDTO);
        }
        Map<String, Object> model = new HashMap<>();
        model.put("heladeras", heladerasDTO);
        context.render("dashboard/heladeras.hbs", model);
    }

    @Override
    public void show(Context context) {
    }
    @Override
    public void create(Context context) {
        //Validaciones
        Validator<Integer> capacidadMax = context.formParamAsClass("capacidadMaxima", Integer.class)
                .check(value -> value > 0, "La capacidad maxima debe ser mayor a 0");
        Validator<Integer> capacidadActual = context.formParamAsClass("capacidadActual", Integer.class)
                .check(value -> value >= 0, "La capacidad actual no puede ser negativa");
        Validator<String> nombreHeladera = context.formParamAsClass("nombreHeladera", String.class)
                .check(Objects::nonNull, "El nombre de la heladera es obligatorio");
        Validator<String> modelo = context.formParamAsClass("modeloHeladera", String.class)
                .check(Objects::nonNull, "El modelo de la heladera es obligatorio");
        String estadoHeladera = context.formParam("estadoHeladera");

        Map<String, List<ValidationError<?>>> errors = Validation.collectErrors(capacidadActual,capacidadMax,nombreHeladera);

        //Errores
        if(!errors.isEmpty()){
            System.out.println(errors);
            context.redirect("/dashboard/heladeras"); // TODO -> Pantalla del form pero mencionando los errores al usuario
            return;
        }
        //Exito
        ModeloDeHeladera modeloDeHeladera = new ModeloDeHeladera(modelo.get().toUpperCase());
        //Harcodeo la temp max y min, tendria sentido agregarle al form creo
        modeloDeHeladera.setTemperaturaMaxima(22f);
        modeloDeHeladera.setTemperaturaMinima(-12f);

        Provincia provincia1 = new Provincia("Córdoba");
        Ubicacion ubicacionA = new Ubicacion(11234f, 6456f, new Calle("Av. Medrano", "6742"));
        ubicacionA.setProvincia(provincia1);

        Heladera heladera = new Heladera(modeloDeHeladera,nombreHeladera.get(),ubicacionA);
        if(estadoHeladera != null){
            heladera.setEstadoHeladera(EstadoHeladera.ACTIVA);
        }else{
            heladera.setEstadoHeladera(EstadoHeladera.INACTIVA);
        }
        heladera.setCapacidadMax(capacidadMax.get());
        heladera.setCapacidadActual(capacidadActual.get());
        heladera.setFechaInicioFuncionamiento(LocalDate.parse(context.formParam("fechaInicioFuncionamiento")));

        withTransaction(()->{
            repositorioHeladeras.guardar(heladera);
        });

        context.redirect("/dashboard/heladeras");
    }

    @Override
    public void save(Context context) {

    }

    @Override
    public void edit(Context context) {
        Optional<Object> posibleHeladera = repositorioHeladeras.buscarPorID(Heladera.class,Long.valueOf(context.pathParam("id")));
        if(posibleHeladera.isPresent()){
            Heladera heladera = (Heladera) posibleHeladera.get();

            HeladeraDTO heladeraDTO = new HeladeraDTO();
            heladeraDTO.setId(heladera.getId());
            heladeraDTO.setNombreIdentificador(heladera.getNombreIdentificador());
            heladeraDTO.setEstadoHeladera(heladera.getEstadoHeladera());
            heladeraDTO.setCapacidadMax(heladera.getCapacidadMax());
            heladeraDTO.setCapacidadActual(heladera.getCapacidadActual());
            if(heladera.getFechaInicioFuncionamiento()!=null){
                heladeraDTO.setFechaInicioFuncionamiento(heladera.getFechaInicioFuncionamiento().toString());
            }
            heladeraDTO.setModeloDeHeladera(heladera.getModelo().getNombreModelo());
            Map<String, Object> model = new HashMap<>();
            model.put("heladera", heladeraDTO);
            model.put("action","/dashboard/heladeras/"+heladeraDTO.getId()+"/edit");
            context.render("/dashboard/forms/heladera.hbs",model);
        }else{
            context.status(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void update(Context context) {
        //Validaciones
        Validator<Integer> capacidadMax = context.formParamAsClass("capacidadMaxima", Integer.class)
                .check(value -> value > 0, "La capacidad maxima debe ser mayor a 0");
        Validator<Integer> capacidadActual = context.formParamAsClass("capacidadActual", Integer.class)
                .check(value -> value >= 0, "La capacidad actual no puede ser negativa");
        Validator<String> nombreHeladera = context.formParamAsClass("nombreHeladera", String.class)
                .check(value -> value != null, "El nombre de la heladera es obligatorio");
        Validator<String> modelo = context.formParamAsClass("modeloHeladera", String.class)
                .check(Objects::nonNull, "El modelo de la heladera es obligatorio");
        String estadoHeladera = context.formParam("estadoHeladera");
        //
        //Errores
        Map<String, List<ValidationError<?>>> errors = Validation.collectErrors(capacidadActual,capacidadMax,nombreHeladera);

        if(!errors.isEmpty()){
            System.out.println(errors);
            context.redirect("/dashboard/heladeras"); // TODO -> Pantalla del form pero mencionando los errores al usuario
            return;
        }
        Optional<Object> posibleHeladeraEncontrada = this.repositorioHeladeras.buscarPorID(Heladera.class,Long.valueOf(context.pathParam("id")));
        if(posibleHeladeraEncontrada.isPresent()){
            Heladera heladera = (Heladera) posibleHeladeraEncontrada.get();
            ModeloDeHeladera modeloDeHeladera = new ModeloDeHeladera(modelo.get());
            heladera.setNombreIdentificador(nombreHeladera.get());
            if(estadoHeladera != null){
                heladera.setEstadoHeladera(EstadoHeladera.ACTIVA);
            }else{
                heladera.setEstadoHeladera(EstadoHeladera.INACTIVA);
            }
            heladera.setCapacidadActual(capacidadActual.get());
            heladera.setCapacidadMax(capacidadMax.get());
            heladera.setFechaInicioFuncionamiento(LocalDate.parse(context.formParam("fechaInicioFuncionamiento")));
            heladera.setModelo(modeloDeHeladera);
            withTransaction(()->{
                repositorioHeladeras.actualizar(heladera);
            });
            context.redirect("/dashboard/heladeras"); //TODO pantalla de exito al actualizar!
        }else{
            context.status(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void delete(Context context) {

    }
    @Override
    public void remove(Context context) {

    }
}
