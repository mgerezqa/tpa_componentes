package controladores;

import domain.donaciones.Dinero;
import domain.donaciones.RegistroDePersonaVulnerable;
import dtos.DineroDTO;
import dtos.RegistroPVDTO;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import repositorios.repositoriosBDD.RepositorioColaboradores;
import repositorios.repositoriosBDD.RepositorioRegistrosVulnerables;
import utils.ICrudViewsHandler;

import java.util.*;

public class ControladorDonacionRegistroPersonaVulnerable implements ICrudViewsHandler, WithSimplePersistenceUnit {

    private RepositorioColaboradores repositorioColaboradores;
    private RepositorioRegistrosVulnerables repositorioRegistrosVulnerables;

    public ControladorDonacionRegistroPersonaVulnerable(RepositorioColaboradores repositorioColaboradores, RepositorioRegistrosVulnerables repositorioRegistrosVulnerables) {
        this.repositorioColaboradores = repositorioColaboradores;
        this.repositorioRegistrosVulnerables = repositorioRegistrosVulnerables;
    }

    @Override
    public void index(Context context) {
        List<RegistroDePersonaVulnerable> registroDePersonaVulnerables = repositorioRegistrosVulnerables.obtenerTodos();
        List<RegistroPVDTO> registroPVDTOS = new ArrayList<>();

        for(RegistroDePersonaVulnerable registro : registroDePersonaVulnerables){
            try {

                RegistroPVDTO dto = new RegistroPVDTO();
                dto.setId(registro.getId());
                dto.setFechaDeDonacion(registro.getFechaDeDonacion());
                dto.setCantidad(registro.getCantidad());

                Long colaboradorId = registro.getColaboradorQueLaDono().getId();
                String nombreColaborador = repositorioColaboradores.obtenerNombreORazonSocialPorId(colaboradorId);

                dto.setNombreColaborador(nombreColaborador);
                dto.setColaboradorId(colaboradorId);

                registroPVDTOS.add(dto);

            }catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        Map<String, List<RegistroPVDTO>> model = new HashMap<>();
        model.put("entregasTarjetas", registroPVDTOS);
        context.render("/dashboard/donaciones/registroPV.hbs", model);
    }

    @Override
    public void delete(Context context) {
        Optional<Object> posibleDonacion =
                this.repositorioRegistrosVulnerables.buscarPorID(RegistroDePersonaVulnerable.class, Long.valueOf(context.pathParam("id")));

        if (posibleDonacion.isEmpty()) {
            context.status(HttpStatus.NOT_FOUND);
            return;
        }

        RegistroDePersonaVulnerable registroDePersonaVulnerable = (RegistroDePersonaVulnerable) posibleDonacion.get();

        registroDePersonaVulnerable.getColaboradorQueLaDono().restarPuntos(registroDePersonaVulnerable.getPuntosOtorgados());

        withTransaction(() -> {
            repositorioRegistrosVulnerables.eliminarPorId(registroDePersonaVulnerable.getId());
            System.out.println("Donacion de registrar persona vulnerable eliminada: " + registroDePersonaVulnerable.getId());
        });

        context.redirect("/dashboard/donaciones/registroPV");
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
