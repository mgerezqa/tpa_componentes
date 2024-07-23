package domain.incidentes;
import domain.heladera.Heladera.EstadoHeladera;
import domain.heladera.Heladera.Heladera;
import domain.usuarios.Tecnico;
import lombok.Getter;
import lombok.Setter;
import utils.notificador.INotificadorIncidentes;
import utils.notificador.NotificadorIncidentes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public abstract class Incidente {
    private Heladera heladera;
    private LocalDateTime fechaYHora;
    private String id;
    private INotificadorIncidentes notificador;

    // tecnico asignado

    // TODO: si no inicializo el notificador de esta manera, no corre el test.
    private List<Tecnico> tecnicos = new ArrayList<>();

    public Incidente(Heladera heladera) {
        this.notificador = new NotificadorIncidentes(tecnicos);
        this.heladera = heladera;

        heladera.agregarIncidente(this); // Persistir en cada heladera
        heladera.setEstadoHeladera(EstadoHeladera.INACTIVA); // Setear cada heladera como inactiva
        notificador.notificarTecnico(this);
    }
}
