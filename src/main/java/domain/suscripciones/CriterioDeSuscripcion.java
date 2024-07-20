package domain.suscripciones;

import domain.heladera.Heladera.Heladera;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public abstract class CriterioDeSuscripcion {
    @Getter
    private String descripcion;
    @Setter @Getter
    private LocalDateTime fecha;

    public abstract boolean verificarCondicion(Heladera heladera);

    public int capacidadActualHeladera(Heladera heladera) {
        return heladera.getCapacidadActual();
    }

    public int capacidadMaximaDeHeladera(Heladera heladera) {
        return heladera.getCapacidadMax();
    }

}
