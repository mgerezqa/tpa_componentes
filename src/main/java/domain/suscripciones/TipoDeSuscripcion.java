package domain.suscripciones;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public abstract class TipoDeSuscripcion {
    @Setter @Getter
    private String descripcion;
    @Setter @Getter
    private LocalDateTime fecha;

}
