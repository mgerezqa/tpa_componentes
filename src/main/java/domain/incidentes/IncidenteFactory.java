package domain.incidentes;
import config.ServiceLocator;
import domain.heladera.Heladera.Heladera;
import domain.usuarios.Usuario;
import dtos.FallaTecnicaDTO;
import repositorios.repositoriosBDD.RepositorioTecnicos;
import utils.asignadorTecnicos.AsignadorDeTecnico;

import java.time.LocalDateTime;

public class IncidenteFactory {

    public static Alerta crearAlerta(Heladera heladera, String tipoAlerta){

        Alerta alerta = new Alerta(heladera);

        alerta.setFechaYHora(LocalDateTime.now());
        alerta.setTipoAlerta(tipoAlerta);

        AsignadorDeTecnico asignadorDeTecnico = ServiceLocator.instanceOf(AsignadorDeTecnico.class);
        asignadorDeTecnico.setTecnicos(ServiceLocator.instanceOf(RepositorioTecnicos.class).buscarTodosTecnicos());
        asignadorDeTecnico.asignarTecnicoA(alerta);

        return alerta;
    }

    // // // // // // // // // // // // // // // // // // // // // // // // // // // // // // // // // // // // // // // //

    public static FallaTecnica crearFallaTecnica(FallaTecnicaDTO fallaTecnicaDTO, Heladera heladera, Usuario usuario){
        FallaTecnica fallaTecnica = new FallaTecnica(heladera);

        fallaTecnica.setFechaYHora(LocalDateTime.now());
        fallaTecnica.setReportadoPor(usuario);
        fallaTecnica.setDescripcion(fallaTecnicaDTO.getDescripcion());
        fallaTecnica.setFoto(fallaTecnicaDTO.getFoto());

        return fallaTecnica;
    }
}
