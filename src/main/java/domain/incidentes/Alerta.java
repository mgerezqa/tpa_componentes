package domain.incidentes;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Alerta extends Incidente {
    private String tipoAlerta;
}