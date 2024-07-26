package domain.incidentes;
import domain.heladera.Heladera.Heladera;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Alerta extends Incidente {
    private String tipoAlerta;

    public Alerta(Heladera heladera) {
        super(heladera);
    }
}