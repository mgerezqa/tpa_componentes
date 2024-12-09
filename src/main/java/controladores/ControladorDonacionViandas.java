package controladores;

import domain.donaciones.Vianda;
import domain.heladera.Heladera.Heladera;
import domain.usuarios.Colaborador;
import domain.usuarios.ColaboradorFisico;
import dtos.ViandaDTO;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import io.javalin.validation.Validation;
import io.javalin.validation.ValidationError;
import io.javalin.validation.Validator;
import repositorios.repositoriosBDD.RepositorioColaboradores;
import repositorios.repositoriosBDD.RepositorioHeladeras;
import repositorios.repositoriosBDD.RepositorioViandas;
import utils.ICrudViewsHandler;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class ControladorDonacionViandas implements ICrudViewsHandler, WithSimplePersistenceUnit {
    private RepositorioViandas repositorioViandas;
    private RepositorioColaboradores repositorioColaboradores;
    private RepositorioHeladeras repositorioHeladeras;

    public ControladorDonacionViandas(RepositorioViandas repositorioViandas, RepositorioColaboradores repositorioColaboradores, RepositorioHeladeras repositorioHeladeras){
        this.repositorioViandas = repositorioViandas;
        this.repositorioColaboradores = repositorioColaboradores;
        this.repositorioHeladeras = repositorioHeladeras;
    }

    @Override
    public void index (Context context){
        List<Vianda> viandas = repositorioViandas.obtenerViandas();
        List<ViandaDTO> viandasDTO = new ArrayList<>();

        for(Vianda vianda : viandas){
            try {
                ViandaDTO viandaDTO = new ViandaDTO();

                viandaDTO.setHeladeraActualId(null);
                viandaDTO.setFechaVencimiento(null);
                viandaDTO.setFechaDonacion(vianda.getFechaDeDonacion());
                viandaDTO.setCantidad(vianda.getCantidad());

                viandaDTO.setId(vianda.getId());

                Long colaboradorId = vianda.getColaboradorQueLaDono().getId();
                String nombreColaborador = repositorioColaboradores.obtenerNombreORazonSocialPorId(colaboradorId);

                viandaDTO.setNombreColaborador(nombreColaborador);
                viandaDTO.setColaboradorDonanteId(colaboradorId);

                viandasDTO.add(viandaDTO);
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        Map<String, Object> model = new HashMap<>();
        model.put("viandas", viandasDTO);
        context.render("dashboard/donaciones/viandas.hbs", model);
    }

    @Override
    public void show(Context context) {

    }

    @Override
    public void create(Context context) {
        // Validar y obtener los datos del formulario
        Validator<Long> colaboradorId = context.formParamAsClass("colaboradorId", Long.class)
                .check(Objects::nonNull, "El id del colaborador es obligatorio");
        Validator<String> heladeraId = context.formParamAsClass("heladeraId", String.class)
                .check(Objects::nonNull, "El id de la heladera es obligatorio");
        Validator<LocalDate> fechaDonacion = context.formParamAsClass("fechaDonacion", LocalDate.class)
                .check(Objects::nonNull, "La fecha de donación es obligatoria");
        Validator<LocalDate> fechaVencimiento = context.formParamAsClass("fechaVencimiento", LocalDate.class)
                .check(Objects::nonNull, "La fecha de vencimiento es obligatoria");

        // Verificar errores de validación
        Map<String, List<ValidationError<?>>> errors = Validation.collectErrors(colaboradorId, heladeraId, fechaDonacion, fechaVencimiento);
        if (!errors.isEmpty()) {
            System.out.println(errors);
            context.redirect("/dashboard/donaciones/viandas.hbs"); // Si hay errores, volver al formulario
            return;
        }

        // Crear una nueva instancia de Vianda
        Vianda nuevaVianda = new Vianda();
        nuevaVianda.setFechaDeDonacion(fechaDonacion.get());
        nuevaVianda.setFechaVencimiento(fechaVencimiento.get());

        // Obtener colaborador y heladera por ID
        ColaboradorFisico colaborador = repositorioColaboradores.obtenerFisicoId(colaboradorId.get());
        Heladera heladera = repositorioHeladeras.obtenerHeladeraPorID(heladeraId.get());

        // Asignar colaborador y heladera a la nueva vianda
        nuevaVianda.setColaboradorQueLaDono(colaborador);
        nuevaVianda.setHeladeraActual(heladera);

        // Guardar la nueva vianda en el repositorio
        repositorioViandas.guardar(nuevaVianda);

        // Redirigir al listado de viandas
        context.redirect("/dashboard/donaciones/viandas.hbs"); // Redireccionar a la vista de listado de viandas
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
        Validator<Heladera> heladera = context.formParamAsClass("heladera", Heladera.class)
                .check(Objects::nonNull, "La heladera es obligatoria");
        Validator<Colaborador> colaborador = context.formParamAsClass("colaboradorQueLaDono", Colaborador.class)
                .check(Objects::nonNull, "El colaborador es obligatorio");
        Validator<LocalDateTime> fechaDonacion = context.formParamAsClass("fechaDonacion", LocalDateTime.class)
                .check(Objects::nonNull, "La fecha es obligatoria");
        Validator<LocalDateTime> fechaVencimiento = context.formParamAsClass("fechaVencimiento", LocalDateTime.class)
                .check(Objects::nonNull, "La fecha es obligatoria");

        Map<String, List<ValidationError<?>>> errors = Validation.collectErrors(heladera, colaborador, fechaDonacion, fechaVencimiento);
        if (!errors.isEmpty()) {
            System.out.println(errors);
            context.redirect("/dashboard/donaciones/dinero");
            return;
        }

        Optional<Object> posibleViandaEncontrada = this.repositorioViandas.buscarPorID(Vianda.class,Long.valueOf(context.pathParam("id")));
        if(posibleViandaEncontrada.isPresent()){
            Vianda vianda = (Vianda) posibleViandaEncontrada.get();
            vianda.setFechaDeDonacion(LocalDate.parse(context.formParam("fechaDonacion")));
            vianda.setFechaVencimiento(LocalDate.parse(context.formParam("fechaDonacion")));
            vianda.setHeladeraActual(heladera.get());
            vianda.setColaboradorQueLaDono(colaborador.get());
            withTransaction(()->{
                repositorioViandas.actualizar(vianda);
            });
            context.redirect("/dashboard/donaciones/vianda"); //TODO pantalla de exito al actualizar!
        }else{
            context.status(HttpStatus.NOT_FOUND);
        }

    }

    @Override
    public void delete(Context context) {
        Long viandaId = Long.valueOf(context.pathParam("id"));
        Vianda vianda = this.repositorioViandas.obtenerPorId(viandaId);
        if(vianda == null){
            context.status(HttpStatus.NOT_FOUND);
            return;
        }

        vianda.getColaboradorQueLaDono().restarPuntos(vianda.getPuntosOtorgados());

        withTransaction(()->{
            repositorioViandas.eliminarPorId(viandaId);
        });
        context.redirect("/dashboard/donaciones/viandas");

    }

    @Override
    public void remove(Context context) {
        Long viandaId = Long.valueOf(context.pathParam("id"));
        Vianda vianda = repositorioViandas.obtenerPorId(viandaId);
        if (vianda != null) {
            vianda.setActiva(false); // Supongamos que hay un campo "activo"
            repositorioViandas.guardar(vianda);
        }
        context.redirect("/dashboard/donaciones/viandas");
    }

}


