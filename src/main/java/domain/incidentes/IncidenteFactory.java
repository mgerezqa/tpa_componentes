package domain.incidentes;
import domain.heladera.Heladera.Heladera;
import dtos.FallaTecnicaInputDTO;

import java.time.LocalDateTime;

public class IncidenteFactory {

    public static Alerta crearAlerta(Heladera heladera, String tipoAlerta){
        return new Alerta(LocalDateTime.now(), heladera, tipoAlerta);
    }

    public static FallaTecnica crearFallaTecnica(Heladera heladera, String reportadoPor, String descripcion, String foto){
        return new FallaTecnica(LocalDateTime.now(), heladera, reportadoPor, descripcion, foto);
    }

}
