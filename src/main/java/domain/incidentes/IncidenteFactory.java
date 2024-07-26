package domain.incidentes;

import domain.heladera.Heladera.Heladera;
import domain.usuarios.Usuario;
import dtos.FallaTecnicaDTO;

import java.time.LocalDateTime;

public class IncidenteFactory {

    public static Alerta crearAlerta(Heladera heladera, String tipoAlerta){
        Alerta alerta = new Alerta(heladera);
        System.out.println(alerta);
        alerta.setHeladera(heladera);
        alerta.setFechaYHora(LocalDateTime.now());
        alerta.setTipoAlerta(tipoAlerta);
        return alerta;
    }

    public static FallaTecnica crearFallaTecnica(FallaTecnicaDTO fallaTecnicaDTO, Heladera heladera, Usuario usuario){
        FallaTecnica fallaTecnica = new FallaTecnica(heladera);

        fallaTecnica.setHeladera(heladera);
        fallaTecnica.setFechaYHora(LocalDateTime.now());
        fallaTecnica.setReportadoPor(usuario);
        fallaTecnica.setDescripcion(fallaTecnicaDTO.getDescripcion());
        fallaTecnica.setFoto(fallaTecnicaDTO.getFoto());

        return fallaTecnica;
    }
}