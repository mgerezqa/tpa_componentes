package domain.suscripciones;

import domain.heladera.Heladera.Heladera;
import lombok.Getter;
import lombok.Setter;

public class SuscripcionPorCantidadDeViandasDisponibles extends TipoDeSuscripcion {

    @Setter @Getter
    private int cantidadDeViandasDisp;

    private final String descripcion = "CANTIDAD_DE_VIANDAS_DISPONIBLES";

    @Override

    public boolean cumpleCriterio(Heladera heladera) {
        return verificarCantidadViandasDisponibles(cantidadDeViandasDisp,heladera);
    }

    public boolean verificarCantidadViandasDisponibles(int n,Heladera heladera)
    {
        return capacidadActualHeladera(heladera) <= n;
    }




}
