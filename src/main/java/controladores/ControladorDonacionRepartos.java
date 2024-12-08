package controladores;

import domain.donaciones.Distribuir;
import dtos.DistribuirDTO;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import repositorios.repositoriosBDD.RepositorioColaboradores;
import repositorios.repositoriosBDD.RepositorioDonacionesReparto;
import utils.ICrudViewsHandler;

import java.util.*;

public class ControladorDonacionRepartos implements ICrudViewsHandler, WithSimplePersistenceUnit {

    private RepositorioDonacionesReparto repositorioDonacionesReparto;
    private RepositorioColaboradores repositorioColaboradores;

    public ControladorDonacionRepartos(RepositorioDonacionesReparto repositorioDonacionesReparto, RepositorioColaboradores repositorioColaboradores) {
        this.repositorioDonacionesReparto = repositorioDonacionesReparto;
        this.repositorioColaboradores =  repositorioColaboradores;
    }

    @Override
    public void index(Context context) {
        List<Distribuir> repartos = repositorioDonacionesReparto.obtenerTodos();
        List<DistribuirDTO> repartosDTO = new ArrayList<>();

        for(Distribuir reparto : repartos){
            try {
                DistribuirDTO repartoDTO = new DistribuirDTO();
                repartoDTO.setId(reparto.getId());
                repartoDTO.setCantidad(reparto.getCantidad());
                repartoDTO.setHeladeraOrigenId(null);
                repartoDTO.setHeladeraDestinoId(null);
                repartoDTO.setFechaDonacion(reparto.getFechaDeDonacion());
                repartoDTO.setMotivo(null);

                Long colaboradorId = reparto.getColaboradorQueLaDono().getId();
                String nombreColaborador = repositorioColaboradores.obtenerNombreORazonSocialPorId(colaboradorId);

                repartoDTO.setNombreColaborador(nombreColaborador);
                repartoDTO.setColaboradorId(colaboradorId);

                repartosDTO.add(repartoDTO);
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }

        Map<String, List<DistribuirDTO>> model = new HashMap<>();
        model.put("repartos", repartosDTO);
        context.render("/dashboard/donaciones/repartos.hbs", model);
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
        Optional<Object> posibleDonacion =
                this.repositorioDonacionesReparto.buscarPorID(Distribuir.class, Long.valueOf(context.pathParam("id")));

        if (posibleDonacion.isEmpty()) {
            context.status(HttpStatus.NOT_FOUND);
            return;
        }

        Distribuir reparto = (Distribuir) posibleDonacion.get();

        reparto.getColaboradorQueLaDono().restarPuntos(reparto.getPuntosOtorgados());

        withTransaction(() -> {
            repositorioDonacionesReparto.eliminarPorId(reparto.getId());
            System.out.println("Donacion de reparto de viandas eliminada: " + reparto.getId());
        });

        context.redirect("/dashboard/donaciones/repartos");
    }

    @Override
    public void remove(Context context) {

    }
}
