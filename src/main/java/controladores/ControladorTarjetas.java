package controladores;

import domain.suscripciones.Suscripcion;
import domain.tarjeta.Tarjeta;
import dtos.SuscripcionDTO;
import dtos.TarjetaDTO;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;
import repositorios.repositoriosBDD.RepositorioTarjetas;
import utils.ICrudViewsHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControladorTarjetas implements ICrudViewsHandler, WithSimplePersistenceUnit {
    private RepositorioTarjetas repositorioTarjetas;

    public ControladorTarjetas(RepositorioTarjetas repositorioTarjetas) {
        this.repositorioTarjetas = repositorioTarjetas;
    }

    @Override
    public void index(Context context) {
        List<Tarjeta> tarjetas = repositorioTarjetas.buscarTarjetas();
        List<TarjetaDTO> tarjetasDTO = new ArrayList<>();
        for (Tarjeta tarjeta : tarjetas) {
            try {
                TarjetaDTO tarjetaDTO = new TarjetaDTO();
                tarjetaDTO.setCodigoIdentificador(tarjeta.getCodigoIdentificador());
                tarjetaDTO.setEstado(tarjeta.getEstado());
                tarjetaDTO.setCantidadMaxDeUso(tarjeta.cantidadLimiteDisponiblePorDia());
                tarjetaDTO.setCantidadUsadaEnElDia(tarjeta.getCantidadUsadaEnElDia());
                tarjetaDTO.setFechaInicioDeFuncionamiento(tarjeta.getFechaInicioDeFuncionamiento().toString());
                tarjetaDTO.setIdColaborador(tarjeta.getColaborador()!=null?tarjeta.getColaborador().getId():null);
                tarjetaDTO.setIdBeneficiario(tarjeta.getVulnerable()!=null?tarjeta.getVulnerable().getId():null);
                tarjetasDTO.add(tarjetaDTO);
            }catch (Exception ex){
                System.out.println(ex.getMessage());
            }
        }
        Map<String,List<TarjetaDTO>> model = new HashMap<>();
        model.put("tarjetas",tarjetasDTO);
        context.render("/dashboard/tarjetas.hbs",model);
    }

    @Override
    public void show(Context context) {

    }

    @Override
    public void create(Context context) {
        Map<String,Object> modal = new HashMap<>();
        modal.put("action","/dashboard/tarjetas");
        modal.put("edit",false);
        context.render("/dashboard/forms/tarjeta.hbs",modal);
    }

    @Override
    public void save(Context context) {

    }

    @Override
    public void edit(Context context) {
        String idParam = context.pathParam("id");
        Map<String,Object> modal = new HashMap<>();
        modal.put("action","/dashboard/tarjetas/"+idParam+"/edit");
        modal.put("edit",true);
        context.render("/dashboard/forms/tarjeta.hbs",modal);
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
