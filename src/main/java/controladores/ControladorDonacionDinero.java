package controladores;
import domain.donaciones.Dinero;
import dtos.DineroDTO;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;
import repositorios.repositoriosBDD.RepositorioDonacionesDinero;
import utils.ICrudViewsHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControladorDonacionDinero implements ICrudViewsHandler, WithSimplePersistenceUnit {

    private RepositorioDonacionesDinero repositorioDonacionesDinero;

    public ControladorDonacionDinero(RepositorioDonacionesDinero repositorioDonacionesDinero) {
        this.repositorioDonacionesDinero = repositorioDonacionesDinero;
    }

    @Override
    public void index(Context context) {
        List<Dinero> donacionesDinero = repositorioDonacionesDinero.obtenerTodas();
        List<DineroDTO> donacionDineroDTO = new ArrayList<>();

        for(Dinero donacionDinero : donacionesDinero){
            try {
                DineroDTO dineroDTO = new DineroDTO();
                dineroDTO.setCantidad(donacionDinero.getCantidad());
                dineroDTO.setFrecuencia(donacionDinero.getFrecuencia());
                dineroDTO.setFechaDeDonacion(donacionDinero.getFechaDeDonacion());
                dineroDTO.setColaboradorId(String.valueOf(donacionDinero.getColaboradorQueLaDono().getId()));
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
