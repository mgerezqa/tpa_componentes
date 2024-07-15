package domain.suscripciones;

import domain.heladera.Heladera.Heladera;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public abstract class TipoDeSuscripcion {
    @Getter
    private String descripcion;
    @Setter @Getter
    private LocalDateTime fecha;

    public abstract boolean verificarCondicion(Heladera heladera);

}
