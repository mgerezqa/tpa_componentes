package controladores;
import domain.donaciones.Dinero;
import domain.donaciones.FrecuenciaDeDonacion;
import domain.heladera.Heladera.EstadoHeladera;
import domain.heladera.Heladera.Heladera;
import domain.heladera.Heladera.ModeloDeHeladera;
import domain.usuarios.Colaborador;
import domain.usuarios.ColaboradorJuridico;
import domain.usuarios.Rubro;
import domain.usuarios.TipoRazonSocial;
import dtos.DineroDTO;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import io.javalin.validation.Validation;
import io.javalin.validation.ValidationError;
import io.javalin.validation.Validator;
import repositorios.repositoriosBDD.RepositorioColaboradores;
import repositorios.repositoriosBDD.RepositorioDonacionesDinero;
import utils.ICrudViewsHandler;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class ControladorDonacionDinero implements ICrudViewsHandler, WithSimplePersistenceUnit {

    private RepositorioDonacionesDinero repositorioDonacionesDinero;
    private RepositorioColaboradores repositorioColaboradores;

    public ControladorDonacionDinero(RepositorioDonacionesDinero repositorioDonacionesDinero, RepositorioColaboradores repositorioColaboradores) {
        this.repositorioDonacionesDinero = repositorioDonacionesDinero;
        this.repositorioColaboradores =  repositorioColaboradores;
    }

    @Override
    public void index(Context context) {
        List<Dinero> donacionesDinero = repositorioDonacionesDinero.obtenerTodas();
        List<DineroDTO> donacionDineroDTO = new ArrayList<>();

        for(Dinero donacionDinero : donacionesDinero){
            try {
                DineroDTO dineroDTO = new DineroDTO();
                dineroDTO.setCantidad(donacionDinero.getCantidad());
                dineroDTO.setFrecuencia(String.valueOf(donacionDinero.getFrecuencia()));
                dineroDTO.setFechaDeDonacion(donacionDinero.getFechaDeDonacion());
                dineroDTO.setColaboradorId(Long.valueOf(String.valueOf(donacionDinero.getColaboradorQueLaDono().getId())));
                donacionDineroDTO.add(dineroDTO);
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }

        Map<String, List<DineroDTO>> model = new HashMap<>();
        model.put("donacionesDinero", donacionDineroDTO);
        context.render("/dashboard/donaciones/dinero.hbs", model);
    }

    @Override
    public void show(Context context) {

    }

    @Override
    public void create(Context context) {
        Map<String,Object> modal = new HashMap<>();
        modal.put("action","/dashboard/donaciones/dinero/create");
        modal.put("edit",false);
        context.render("/dashboard/forms/dineroForm.hbs",modal);
    }

    @Override
    public void save(Context context) {
        //Validaciones
        Validator<Integer> cantidad = context.formParamAsClass("cantidad", Integer.class)
                .check(value -> value > 0, "La cantidad debe ser mayor a 0");
        Validator<String> nombreColaborador = context.formParamAsClass("nombreColaborador", String.class)
                .check(Objects::nonNull, "El nombre del colaborador es obligatorio");
        Validator<LocalDateTime> fechaDonacion = context.formParamAsClass("fechaDonacion", LocalDateTime.class)
                .check(Objects::nonNull, "La fecha es obligatoria");

        Map<String, List<ValidationError<?>>> errors = Validation.collectErrors(cantidad, nombreColaborador, fechaDonacion);
        if (!errors.isEmpty()) {
            System.out.println(errors);
            context.redirect("/dashboard/donaciones/dinero");
            return;
        }

        //Exito
        ColaboradorJuridico colaborador = new ColaboradorJuridico("Donadores SA", TipoRazonSocial.EMPRESA, Rubro.CONSTRUCCION);
        Dinero donacionDinero = new Dinero(cantidad.get(), FrecuenciaDeDonacion.FRECUENCIA_MENSUAL, LocalDate.now(), colaborador);

        withTransaction(() -> {
            repositorioDonacionesDinero.guardar(donacionDinero);
        });

        context.redirect("/dashboard/donaciones/dinero.hbs");
    }

    @Override
    public void edit(Context context) {

    }

    @Override
    public void update(Context context) {
        //Validaciones
        Validator<Integer> cantidad = context.formParamAsClass("cantidad", Integer.class)
                .check(value -> value > 0, "La cantidad debe ser mayor a 0");
        Validator<Colaborador> colaborador = context.formParamAsClass("colaboradorQueLaDono", Colaborador.class)
                .check(Objects::nonNull, "El colaborador es obligatorio");
        Validator<LocalDateTime> fechaDonacion = context.formParamAsClass("fechaDonacion", LocalDateTime.class)
                .check(Objects::nonNull, "La fecha es obligatoria");

        Map<String, List<ValidationError<?>>> errors = Validation.collectErrors(cantidad, colaborador, fechaDonacion);
        if (!errors.isEmpty()) {
            System.out.println(errors);
            context.redirect("/dashboard/donaciones/dinero");
            return;
        }

        Optional<Object> posibleDonacionDeDineroEncontrada = this.repositorioDonacionesDinero.buscarPorID(Dinero.class,Long.valueOf(context.pathParam("id")));
        if(posibleDonacionDeDineroEncontrada.isPresent()){
            Dinero donacionDinero = (Dinero) posibleDonacionDeDineroEncontrada.get();
            donacionDinero.setCantidad(cantidad.get());
            donacionDinero.setFechaDeDonacion(LocalDate.parse(context.formParam("fechaDonacion")));
            donacionDinero.setFrecuencia(FrecuenciaDeDonacion.valueOf(String.valueOf(context.formParam("frecuencia"))));
            donacionDinero.setColaboradorQueLaDono(colaborador.get());
            withTransaction(()->{
                repositorioDonacionesDinero.actualizar(donacionDinero);
            });
            context.redirect("/dashboard/donaciones/dinero"); //TODO pantalla de exito al actualizar!
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
