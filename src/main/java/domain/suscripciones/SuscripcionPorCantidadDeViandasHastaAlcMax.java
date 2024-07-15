package domain.suscripciones;

import domain.heladera.Heladera.Heladera;
import lombok.Getter;
import lombok.Setter;

public class SuscripcionPorCantidadDeViandasHastaAlcMax extends TipoDeSuscripcion {

    @Getter @Setter
    private int cantidadDeViandasHastaAlcMax;

    private final String descripcion = "CANTIDAD_DE_VIANDAS_HASTA_ALCANZAR_MAX";


    @Override
    public boolean verificarCondicion(Heladera heladera) {
        return heladera.getCondicionActual().verificarCantidadViandasHastaAlcanzarMaximo(cantidadDeViandasHastaAlcMax);
    }
}
