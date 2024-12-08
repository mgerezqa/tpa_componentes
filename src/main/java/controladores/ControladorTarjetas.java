package controladores;

import domain.persona.PersonaVulnerable;
import domain.tarjeta.Tarjeta;
import domain.tarjeta.TarjetaColaborador;
import domain.tarjeta.TarjetaVulnerable;
import domain.usuarios.Colaborador;
import domain.usuarios.ColaboradorFisico;
import dtos.TarjetaDTO;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import org.apache.commons.lang3.StringUtils;
import repositorios.repositoriosBDD.RepositorioColaboradores;
import repositorios.repositoriosBDD.RepositorioTarjetas;
import repositorios.repositoriosBDD.RepositorioVulnerables;
import server.exceptions.TarjetasException;
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
            throw new TarjetasException("No se pudieron cargar las tarjetas", e);
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
        try {
            boolean activo = context.formParam("estadoBeneficiario") != null;
            String fechaDeAlta = context.formParam("fechaDeAltaDeUsoTarjeta");
            
            withTransaction(() -> {
                Tarjeta tarjeta = crearTarjetaSegunTipo(context);
                configurarTarjeta(tarjeta, fechaDeAlta, activo);
                repositorioTarjetas.guardar(tarjeta);
            });
            
            context.redirect("/dashboard/tarjetas");
        } catch (Exception e) {
            throw new TarjetasException("Error al guardar la tarjeta", e);
        }
    }

    @Override
    public void edit(Context context) {
        String idParam = context.pathParam("id");
        List<Long> colaboradoresPosibles = repositorioColaboradores.obtenerColaboradoresFisicosActivos()
            .stream()
            .map(Colaborador::getId)
            .collect(Collectors.toList());
        List<Long> beneficiariosPosibles = repositorioVulnerables.obtenerPersonasVulnerables()
            .stream()
            .map(PersonaVulnerable::getId)
            .collect(Collectors.toList());

        Optional<Tarjeta> posibleTarjeta = repositorioTarjetas.obtenerPorUUID(idParam);
        
        if (posibleTarjeta.isPresent()) {
            Map<String, Object> modal = new HashMap<>();
            modal.put("colaboradores", colaboradoresPosibles);
            modal.put("beneficiarios", beneficiariosPosibles);
            modal.put("action", "/dashboard/tarjetas/" + idParam + "/edit");
            modal.put("tarjeta", convertToDTO(posibleTarjeta.get()));
            modal.put("edit", true);
            context.render("/dashboard/forms/tarjeta.hbs", modal);
        } else {
            context.status(HttpStatus.NOT_FOUND);
        }
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
                configurarTarjeta(tarjeta, fechaDeAlta, activo);
                repositorioTarjetas.actualizar(tarjeta);

                if(context.formParam("tarjetaDeBeneficiarioColaboradorId") != null){
                    Long idColab = Long.valueOf(context.formParam("tarjetaDeBeneficiarioColaboradorId"));
                    TarjetaColaborador tarjetaColaborador = (TarjetaColaborador) tarjeta;
                    Optional<ColaboradorFisico> posibleColaborador = repositorioColaboradores.buscarPorID(ColaboradorFisico.class, idColab)
                        .map(ColaboradorFisico.class::cast);
                    posibleColaborador.ifPresent(tarjetaColaborador::setColaborador);
                }
                if(context.formParam("tarjetaDeColaboradorBeneficiarioId") != null){
                    TarjetaVulnerable tarjetaVulnerable = (TarjetaVulnerable) tarjeta;
                    Long idBene = Long.valueOf(context.formParam("tarjetaDeColaboradorBeneficiarioId"));
                    Optional<Object> posibleBeneficiario = repositorioVulnerables.buscarPorID(PersonaVulnerable.class,idBene);
                    posibleBeneficiario.ifPresent(o -> tarjetaVulnerable.setVulnerable((PersonaVulnerable) o));
                }
                configurarTarjeta(tarjeta, fechaDeAlta, activo);

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

    private Tarjeta crearTarjetaSegunTipo(Context context) {
        String idColaborador = context.formParam("tarjetaDeBeneficiarioColaboradorId");
        String idBeneficiario = context.formParam("tarjetaDeColaboradorBeneficiarioId");

        if (!StringUtils.isEmpty(idColaborador)) {
            return crearTarjetaColaborador(Long.valueOf(idColaborador));
        } else if (!StringUtils.isEmpty(idBeneficiario)) {
            return crearTarjetaVulnerable(Long.valueOf(idBeneficiario));
        }
        throw new TarjetasException("Debe especificar un colaborador o beneficiario para la tarjeta");
    }
    private TarjetaColaborador crearTarjetaColaborador(Long idColaborador) {
        ColaboradorFisico colaborador = repositorioColaboradores.buscarPorID(ColaboradorFisico.class, idColaborador)
                .map(ColaboradorFisico.class::cast)
                .orElseThrow(() -> new TarjetasException("Colaborador no encontrado"));

        TarjetaColaborador tarjeta = TarjetaColaborador.of(colaborador);
        return tarjeta;
    }
    private TarjetaVulnerable crearTarjetaVulnerable(Long idBeneficiario) {
        PersonaVulnerable vulnerable = repositorioVulnerables.buscarPorID(PersonaVulnerable.class, idBeneficiario)
                .map(PersonaVulnerable.class::cast)
                .orElseThrow(() -> new TarjetasException("Beneficiario no encontrado"));

        TarjetaVulnerable tarjeta = new TarjetaVulnerable();
        tarjeta.setVulnerable(vulnerable);
        return tarjeta;
    }
    private void configurarTarjeta(Tarjeta tarjeta, String fechaDeAlta, boolean activo) {
        if (fechaDeAlta == null) {
            throw new TarjetasException("La fecha de alta es requerida");
        }
        tarjeta.setFechaInicioDeFuncionamiento(LocalDate.parse(fechaDeAlta));
        tarjeta.setEstado(activo);
    }
    private TarjetaDTO convertToDTO(Tarjeta tarjeta) {
        TarjetaDTO tarjetaDTO = new TarjetaDTO();
        tarjetaDTO.setCodigoIdentificador(tarjeta.getCodigoIdentificador());
        tarjetaDTO.setEstado(tarjeta.getEstado());
        tarjetaDTO.setCantidadUsadaEnElDia(tarjeta.getCantidadUsadaEnElDia());
        tarjetaDTO.setFechaInicioDeFuncionamiento(tarjeta.getFechaInicioDeFuncionamiento().toString());

        if (tarjeta instanceof TarjetaColaborador) {
            TarjetaColaborador tarjetaColaborador = (TarjetaColaborador) tarjeta;
            tarjetaDTO.setIdColaborador(tarjetaColaborador.getColaborador() != null ?
                    tarjetaColaborador.getColaborador().getId() : null);
            tarjetaDTO.setCantidadMaxDeUso(null);
            tarjetaDTO.setIdBeneficiario(null);
        } else if (tarjeta instanceof TarjetaVulnerable) {
            TarjetaVulnerable tarjetaVulnerable = (TarjetaVulnerable) tarjeta;
            tarjetaDTO.setIdBeneficiario(tarjetaVulnerable.getVulnerable() != null ?
                    tarjetaVulnerable.getVulnerable().getId() : null);
            tarjetaDTO.setCantidadMaxDeUso(tarjetaVulnerable.cantidadLimiteDisponiblePorDia());
            tarjetaDTO.setIdColaborador(null);
        }
        return tarjetaDTO;
    }
}
