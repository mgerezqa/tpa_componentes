package domain.suscripciones;

import domain.heladera.Heladera.Heladera;
import lombok.Getter;
import lombok.Setter;

public class CriterioPorDesperfectoH extends CriterioDeSuscripcion {

    @Getter
    private final String descripcion = "DESPERFECTO";

    @Override
    public boolean verificarCondicion(Heladera heladera) {
        return verificarDesperfecto(heladera);
    }

    public boolean verificarDesperfecto(Heladera heladera) {
        return !heladera.estaActivaHeladera();
    }
}
