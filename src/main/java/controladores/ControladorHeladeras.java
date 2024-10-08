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
import io.javalin.validation.ValidationError;
import io.javalin.validation.Validator;
import repositorios.repositoriosBDD.RepositorioHeladeras;
import utils.ICrudViewsHandler;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
                .check(value -> value != null, "El nombre de la heladera es obligatorio");
        String estadoHeladera = context.formParam("estadoHeladera");

        Map<String, List<ValidationError<Integer>>> errors = capacidadMax.errors();
        //Errores
        if(!errors.isEmpty()){
            System.out.println(errors);
            context.redirect("/dashboard/heladeras"); // TODO -> Pantalla del form pero mencionando los errores al usuario
            return;
        }
        //Exito
        ModeloDeHeladera modelo = new ModeloDeHeladera(context.formParam("modeloHeladera").toUpperCase());
        modelo.setTemperaturaMaxima(22f);
        modelo.setTemperaturaMinima(-12f);
        Provincia provincia1 = new Provincia("Córdoba");
        Ubicacion ubicacionA = new Ubicacion(11234f, 6456f, new Calle("Av. Medrano", "6742"));
        ubicacionA.setProvincia(provincia1);

        Heladera heladera = new Heladera(modelo,nombreHeladera.get(),ubicacionA);
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
        String estadoHeladera = context.formParam("estadoHeladera");
        //
        Optional<Object> posibleHeladeraEncontrada = this.repositorioHeladeras.buscarPorID(Heladera.class,Long.valueOf(context.pathParam("id")));
        if(posibleHeladeraEncontrada.isPresent()){
            Heladera heladera = (Heladera) posibleHeladeraEncontrada.get();
            heladera.setNombreIdentificador(nombreHeladera.get());
            if(estadoHeladera != null){
                heladera.setEstadoHeladera(EstadoHeladera.ACTIVA);
            }else{
                heladera.setEstadoHeladera(EstadoHeladera.INACTIVA);
            }
            heladera.setCapacidadActual(capacidadActual.get());
            heladera.setCapacidadMax(capacidadMax.get());
            heladera.setFechaInicioFuncionamiento(LocalDate.parse(context.formParam("fechaInicioFuncionamiento")));
            withTransaction(()->{
                repositorioHeladeras.actualizar(heladera);
            });
            context.redirect("/dashboard/heladeras"); //TODO pantalla de exito al actualizar!
        }else{
            context.status(HttpStatus.NOT_FOUND);
            return;
        }
    }

    @Override
    public void delete(Context context) {

    }
}
