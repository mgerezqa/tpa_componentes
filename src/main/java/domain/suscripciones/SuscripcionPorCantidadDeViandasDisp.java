package domain.suscripciones;

import domain.heladera.Heladera.Heladera;
import lombok.Getter;
import lombok.Setter;

public class SuscripcionPorCantidadDeViandasDisp extends TipoDeSuscripcion {

    @Setter @Getter
    private int cantidadDeViandasDisp;

    private final String descripcion = "CANTIDAD_DE_VIANDAS_DISPONIBLES";

    @Override
    public boolean verificarCondicion(Heladera heladera) {
        return heladera.getCondicionActual().verificarCantidadViandasDisponibles(cantidadDeViandasDisp);
    }
}
