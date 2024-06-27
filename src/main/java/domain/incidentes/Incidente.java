package domain.incidentes;
import domain.heladera.Heladera.EstadoHeladera;
import domain.heladera.Heladera.Heladera;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public abstract class Incidente {
    private Heladera heladera;
    private LocalDateTime fechaYHora;
    private List<Visita> visitas = new ArrayList<>();

    public Incidente(Heladera heladera, LocalDateTime fechaYHora) {
        this.heladera = heladera;
        this.fechaYHora = fechaYHora;

        heladera.agregarIncidente(this); // Persistir en cada heladera
        heladera.setEstadoHeladera(EstadoHeladera.INACTIVA); // Setear cada heladera como inactiva

    }

    public void registrarVisita(Visita visita) {
        visitas.add(visita);
    }

    // CASO DE USO REGISTRAR VISITA !?

}
