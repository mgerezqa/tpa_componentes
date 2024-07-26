package domain.visitas;

import domain.heladera.Heladera.EstadoHeladera;
import domain.heladera.Heladera.Heladera;
import domain.usuarios.Tecnico;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Visita {
    private Tecnico tecnico;
    private Heladera heladera;
    private LocalDateTime fechaVisita;
    private String comentario;
    private String foto;

    public void incidenteResuelto(Boolean resuelto){
        if(resuelto) {
            heladera.setEstadoHeladera(EstadoHeladera.ACTIVA);
        }
    }
}
