package controladores;

import domain.donaciones.Dinero;
import domain.donaciones.MantenerHeladera;
import domain.heladera.Heladera.Heladera;
import dtos.DineroDTO;
import dtos.MantenimientoDTO;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import repositorios.repositoriosBDD.RepositorioColaboradores;
import repositorios.repositoriosBDD.RepositorioHeladeras;
import repositorios.repositoriosBDD.RepositorioMantenciones;
import utils.ICrudViewsHandler;

import java.util.*;

public class ControladorDonacionMantenimiento implements ICrudViewsHandler, WithSimplePersistenceUnit {

    private RepositorioColaboradores repositorioColaboradores;
    private RepositorioMantenciones repositorioMantenciones;
    private RepositorioHeladeras repositorioHeladeras;

    public ControladorDonacionMantenimiento(RepositorioColaboradores repositorioColaboradores, RepositorioMantenciones repositorioMantenciones, RepositorioHeladeras repositorioHeladeras) {
        this.repositorioColaboradores = repositorioColaboradores;
        this.repositorioMantenciones = repositorioMantenciones;
        this.repositorioHeladeras = repositorioHeladeras;
    }

    @Override
    public void index(Context context) {
        List<MantenerHeladera> mantenimientos = repositorioMantenciones.obtenerTodas();
        List<MantenimientoDTO> mantenimientoDTO = new ArrayList<>();

        for(MantenerHeladera mantenimiento : mantenimientos){
            try {
                MantenimientoDTO mantenerDTO = new MantenimientoDTO();

                mantenerDTO.setId(mantenimiento.getId());
                mantenerDTO.setMesesPuntuados(mantenimiento.getMesesPuntarizados());
                mantenerDTO.setFechaDeDonacion(mantenimiento.getFechaDeDonacion());

                Long colaboradorId = mantenimiento.getColaboradorQueLaDono().getId();
                Long heladeraId = mantenimiento.getHeladera().getId();

                String nombreColaborador = repositorioColaboradores.obtenerNombreORazonSocialPorId(colaboradorId);
                String nombreHeladera = repositorioHeladeras.obtenerNombreHeladeraPorID(heladeraId);

                mantenerDTO.setNombreColaborador(nombreColaborador);
                mantenerDTO.setColaboradorID(colaboradorId);
                mantenerDTO.setHeladeraOrigen(nombreHeladera);
                mantenerDTO.setHeladeraID(heladeraId);

                mantenimientoDTO.add(mantenerDTO);
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }

        Map<String, List<MantenimientoDTO>> model = new HashMap<>();
        model.put("mantenimientos", mantenimientoDTO);
        context.render("/dashboard/donaciones/mantenimiento.hbs", model);
    }

    @Override
    public void delete(Context context) {
        Optional<Object> posibleDonacion =
                this.repositorioMantenciones.buscarPorID(MantenerHeladera.class, Long.valueOf(context.pathParam("id")));

        if (posibleDonacion.isEmpty()) {
            context.status(HttpStatus.NOT_FOUND);
            return;
        }

        MantenerHeladera mantenerHeladera = (MantenerHeladera) posibleDonacion.get();

        mantenerHeladera.getColaboradorQueLaDono().restarPuntos(mantenerHeladera.getPuntosOtorgados());

        withTransaction(() -> {
            repositorioMantenciones.eliminarPorId(mantenerHeladera.getId());
            System.out.println("Donacion de dinero eliminada: " + mantenerHeladera.getId());
        });

        context.redirect("/dashboard/donaciones/mantenimientos");
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
    public void remove(Context context) {

    }
}
