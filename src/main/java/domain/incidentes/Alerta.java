package domain.incidentes;
import domain.heladera.Heladera.Heladera;
import domain.incidentes.Incidente;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class Alerta extends Incidente {
    private String tipoAlerta;

    public Alerta(Heladera heladera) {
        super(heladera);
    }
}
