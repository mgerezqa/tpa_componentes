package domain.incidentes;
import domain.heladera.Heladera.EstadoHeladera;
import domain.heladera.Heladera.Heladera;
import lombok.Getter;
import lombok.Setter;
import utils.notificador.NotificadorIncidentes;

import java.time.LocalDateTime;

@Getter
@Setter
public abstract class Incidente {
    private Heladera heladera;
    private LocalDateTime fechaYHora;
    private String id;
    private NotificadorIncidentes notificador;

    public Incidente() {
        heladera.agregarIncidente(this); // Persistir en cada heladera
        heladera.setEstadoHeladera(EstadoHeladera.INACTIVA); // Setear cada heladera como inactiva
        notificador.notificarTecnico(this);
    }
}
