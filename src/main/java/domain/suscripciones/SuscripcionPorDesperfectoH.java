package domain.suscripciones;

import domain.heladera.Heladera.Heladera;
import lombok.Getter;

public class SuscripcionPorDesperfectoH extends TipoDeSuscripcion {

    @Getter
    private final String descripcion = "DESPERFECTO";

    @Override
    public boolean cumpleCriterio(Heladera heladera) {
        return verificarDesperfecto(heladera);
    }

    public boolean verificarDesperfecto(Heladera heladera) {
        return !heladera.estaActivaHeladera();
    }
}
