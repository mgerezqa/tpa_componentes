package domain.heladera.Heladera;
import lombok.Getter;
import java.time.LocalDate;

@Getter
public class HistorialDeEstados {

    private EstadoHeladera estadoHeladera;
    private LocalDate fechaDeCambio;
    private String descripcion;

    public HistorialDeEstados(EstadoHeladera estadoHeladera, LocalDate fechaDeCambio, String descripcion) {
        this.estadoHeladera = estadoHeladera;
        this.fechaDeCambio = fechaDeCambio;
        this.descripcion = descripcion;
    }

}
