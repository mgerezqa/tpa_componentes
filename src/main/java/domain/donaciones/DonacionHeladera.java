package domain.donaciones;

import domain.heladera.Heladera;

public class DonacionHeladera extends Donacion{
    private Heladera unaHeladera;

    public DonacionHeladera(Heladera unaHeladera) {
        super(TipoDonacion.DONA_HELADERA);
        this.unaHeladera = unaHeladera;
    }
}
