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

    public abstract boolean cumpleCriterio(Heladera heladera);

    public int capacidadActualHeladera(Heladera heladera) {
        return heladera.getCapacidadActual();
    }

    public int capacidadMaximaDeHeladera(Heladera heladera) {
        return heladera.getCapacidadMax();
    }

}
