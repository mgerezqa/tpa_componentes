package domain.suscripciones;

import domain.heladera.Heladera.Heladera;
import lombok.Getter;
import lombok.Setter;

public class SuscripcionPorDesperfectoH extends  TipoDeSuscripcion{

    @Getter
    private final String descripcion = "DESPERFECTO";

    @Getter @Setter
    private boolean desperfecto;


    @Override
    public boolean verificarCondicion(Heladera heladera) {
        return heladera.getCondicionActual().verificarDesperfecto();
    }
}
