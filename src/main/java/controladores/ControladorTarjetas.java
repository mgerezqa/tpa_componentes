package controladores;

import domain.tarjeta.Tarjeta;

import dtos.TarjetaDTO;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import io.javalin.validation.Validation;
import io.javalin.validation.ValidationError;
import repositorios.repositoriosBDD.RepositorioTarjetas;
import utils.ICrudViewsHandler;

import java.util.*;

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

        Optional<Tarjeta> posibleTarjeta = repositorioTarjetas.obtenerPorUUID(idParam);
        if(posibleTarjeta.isPresent()){
            Tarjeta tarjeta = posibleTarjeta.get();

            TarjetaDTO tarjetaDTO = new TarjetaDTO();
            tarjetaDTO.setCodigoIdentificador(tarjeta.getCodigoIdentificador());
            tarjetaDTO.setEstado(tarjeta.getEstado());
            tarjetaDTO.setCantidadMaxDeUso(tarjeta.cantidadLimiteDisponiblePorDia());
            tarjetaDTO.setCantidadUsadaEnElDia(tarjeta.getCantidadUsadaEnElDia());
            tarjetaDTO.setFechaInicioDeFuncionamiento(tarjeta.getFechaInicioDeFuncionamiento().toString());
            tarjetaDTO.setIdColaborador(tarjeta.getColaborador()!=null?tarjeta.getColaborador().getId():null);
            tarjetaDTO.setIdBeneficiario(tarjeta.getVulnerable()!=null?tarjeta.getVulnerable().getId():null);

            Map<String,Object> modal = new HashMap<>();
            modal.put("action","/dashboard/tarjetas/"+idParam+"/edit");
            modal.put("tarjeta",tarjetaDTO);
            modal.put("edit",true);
            context.render("/dashboard/forms/tarjeta.hbs",modal);
        }else{
            context.status(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void update(Context context) {
        //Validaciones
        boolean activo = context.formParam("estadoTarjeta")!= null;

        //Errores
        Map<String, List<ValidationError<?>>> errors = Validation.collectErrors();

        if(!errors.isEmpty()){
            System.out.println(errors);
            context.redirect("/dashboard/tarjetas"); // TODO -> Pantalla del form pero mencionando los errores al usuario
            return;
        }

        //EXITO
        String idParam = context.pathParam("id");

        Optional<Tarjeta> posibleTarjeta = repositorioTarjetas.obtenerPorUUID(idParam);
        if(posibleTarjeta.isPresent()){
            Tarjeta tarjeta = posibleTarjeta.get();
            System.out.println("HOLA");
            withTransaction(()->{
                tarjeta.setEstado(activo);
                repositorioTarjetas.actualizar(tarjeta);
            });
            context.redirect("/dashboard/tarjetas");
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
