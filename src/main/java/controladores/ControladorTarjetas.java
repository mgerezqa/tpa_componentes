package controladores;

import domain.heladera.Heladera.Heladera;
import domain.persona.PersonaVulnerable;
import domain.tarjeta.Tarjeta;

import domain.usuarios.Colaborador;
import domain.usuarios.ColaboradorFisico;
import dtos.TarjetaDTO;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import io.javalin.validation.Validation;
import io.javalin.validation.ValidationError;
import repositorios.repositoriosBDD.RepositorioColaboradores;
import repositorios.repositoriosBDD.RepositorioTarjetas;
import repositorios.repositoriosBDD.RepositorioVulnerables;
import utils.ICrudViewsHandler;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class ControladorTarjetas implements ICrudViewsHandler, WithSimplePersistenceUnit {
    private RepositorioTarjetas repositorioTarjetas;
    private RepositorioVulnerables repositorioVulnerables;
    private RepositorioColaboradores repositorioColaboradores;

    public ControladorTarjetas(RepositorioTarjetas repositorioTarjetas, RepositorioVulnerables repositorioVulnerables, RepositorioColaboradores repositorioColaboradores) {
        this.repositorioTarjetas = repositorioTarjetas;
        this.repositorioVulnerables = repositorioVulnerables;
        this.repositorioColaboradores = repositorioColaboradores;
    }

    @Override
    public void index(Context context) {
        try {
            List<Tarjeta> tarjetas = repositorioTarjetas.buscarTarjetas();
            List<TarjetaDTO> tarjetasDTO = tarjetas.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

            Map<String, List<TarjetaDTO>> model = Map.of("tarjetas", tarjetasDTO);
            context.render("/dashboard/tarjetas.hbs", model);
            
        } catch (Exception e) {
            context.status(HttpStatus.INTERNAL_SERVER_ERROR);
            Map<String, String> error = Map.of("error", "Error al cargar las tarjetas: " + e.getMessage());
            context.render("/dashboard/error.hbs", error);
            e.printStackTrace(); // Reemplazar con un sistema de logging apropiado
        }
    }
    

    @Override
    public void create(Context context) {
        List<Long> colaboradoresPosibles = repositorioColaboradores.obtenerColaboradoresFisicosActivos().stream().map(Colaborador::getId).collect(Collectors.toList());
        List<Long> beneficiariosPosibles = repositorioVulnerables.obtenerPersonasVulnerables().stream().map(PersonaVulnerable::getId).collect(Collectors.toList());

        Map<String,Object> modal = new HashMap<>();
        modal.put("colaboradores",colaboradoresPosibles);
        modal.put("beneficiarios",beneficiariosPosibles);
        modal.put("action","/dashboard/tarjetas");
        modal.put("edit",false);

        context.render("/dashboard/forms/tarjeta.hbs",modal);
    }

    @Override
    public void save(Context context) {
        boolean activo = context.formParam("estadoBeneficiario")!= null;
        String fechaDeAlta = context.formParam("fechaDeAltaDeUsoTarjeta");

        withTransaction(()->{
            Tarjeta tarjeta = new Tarjeta();
            
            if(!context.formParam("tarjetaDeBeneficiarioColaboradorId").isEmpty()){
                Long idColab = Long.valueOf(context.formParam("tarjetaDeBeneficiarioColaboradorId"));
                Optional<ColaboradorFisico> posibleColaborador = repositorioColaboradores.buscarPorID(ColaboradorFisico.class, idColab)
                    .map(ColaboradorFisico.class::cast);
                posibleColaborador.ifPresent(tarjeta::setColaborador);

            }
            if(!context.formParam("tarjetaDeColaboradorBeneficiarioId").isEmpty()){
                Long idBene = Long.valueOf(context.formParam("tarjetaDeColaboradorBeneficiarioId"));
                Optional<PersonaVulnerable> posibleBeneficiario = repositorioVulnerables.buscarPorID(PersonaVulnerable.class,idBene)
                    .map(PersonaVulnerable.class::cast);
                posibleBeneficiario.ifPresent(tarjeta::setVulnerable);

            }

            tarjeta.setFechaInicioDeFuncionamiento(LocalDate.parse(fechaDeAlta));
            tarjeta.setEstado(activo);
            repositorioTarjetas.guardar(tarjeta);
        });
        context.redirect("/dashboard/tarjetas");
    }

    @Override
    public void edit(Context context) {
        String idParam = context.pathParam("id");
        List<Long> colaboradoresPosibles = repositorioColaboradores.obtenerColaboradoresFisicosActivos().stream().map(Colaborador::getId).collect(Collectors.toList());
        List<Long> beneficiariosPosibles = repositorioVulnerables.obtenerPersonasVulnerables().stream().map(PersonaVulnerable::getId).collect(Collectors.toList());
        Optional<Tarjeta> posibleTarjeta = repositorioTarjetas.obtenerPorUUID(idParam);
        if(posibleTarjeta.isPresent()){
            Tarjeta tarjeta = posibleTarjeta.get();

            TarjetaDTO tarjetaDTO = this.convertToDTO(tarjeta);

            Map<String,Object> modal = new HashMap<>();
            modal.put("colaboradores",colaboradoresPosibles);
            modal.put("beneficiarios",beneficiariosPosibles);
            modal.put("action","/dashboard/tarjetas/"+idParam+"/edit");
            modal.put("tarjeta",tarjetaDTO);
            modal.put("edit",true);
            context.render("/dashboard/forms/tarjeta.hbs",modal);
        }else{
            context.status(HttpStatus.NOT_FOUND);
        }
    }
    private TarjetaDTO convertToDTO(Tarjeta tarjeta){
        TarjetaDTO tarjetaDTO = new TarjetaDTO();
        tarjetaDTO.setCodigoIdentificador(tarjeta.getCodigoIdentificador());
        tarjetaDTO.setEstado(tarjeta.getEstado());
        tarjetaDTO.setCantidadMaxDeUso(tarjeta.cantidadLimiteDisponiblePorDia());
        tarjetaDTO.setCantidadUsadaEnElDia(tarjeta.getCantidadUsadaEnElDia());
        tarjetaDTO.setFechaInicioDeFuncionamiento(tarjeta.getFechaInicioDeFuncionamiento().toString());
        tarjetaDTO.setIdColaborador(tarjeta.getColaborador()!=null?tarjeta.getColaborador().getId():null);
        tarjetaDTO.setIdBeneficiario(tarjeta.getVulnerable()!=null?tarjeta.getVulnerable().getId():null);
        return tarjetaDTO;
    }
    @Override
    public void update(Context context) {
        boolean activo = context.formParam("estadoBeneficiario")!= null;
        String fechaDeAlta = context.formParam("fechaDeAltaDeUsoTarjeta");

        String idParam = context.pathParam("id");

        Optional<Tarjeta> posibleTarjeta = repositorioTarjetas.obtenerPorUUID(idParam);
        if(posibleTarjeta.isPresent()){
            Tarjeta tarjeta = posibleTarjeta.get();
            
            withTransaction(()->{
                if(!context.formParam("tarjetaDeBeneficiarioColaboradorId").isEmpty()){
                    Long idColab = Long.valueOf(context.formParam("tarjetaDeBeneficiarioColaboradorId"));
                    Optional<ColaboradorFisico> posibleColaborador = repositorioColaboradores.buscarPorID(ColaboradorFisico.class, idColab)
                        .map(ColaboradorFisico.class::cast);
                    posibleColaborador.ifPresent(tarjeta::setColaborador);
                }
                if(!context.formParam("tarjetaDeColaboradorBeneficiarioId").isEmpty()){
                    Long idBene = Long.valueOf(context.formParam("tarjetaDeColaboradorBeneficiarioId"));
                    Optional<Object> posibleBeneficiario = repositorioVulnerables.buscarPorID(PersonaVulnerable.class,idBene);
                    posibleBeneficiario.ifPresent(o -> tarjeta.setVulnerable((PersonaVulnerable) o));
                }

                assert fechaDeAlta != null;
                tarjeta.setFechaInicioDeFuncionamiento(LocalDate.parse(fechaDeAlta));
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
        String idParam = context.pathParam("id");
        
        Map<String, Object> model = new HashMap<>();
        model.put("action", "/dashboard/tarjetas/" + idParam + "/delete");
        
        context.render("/dashboard/delete/tarjeta.hbs", model);
    }

    @Override
    public void remove(Context context) {

    }
    @Override
    public void show(Context context) {

    }
}
