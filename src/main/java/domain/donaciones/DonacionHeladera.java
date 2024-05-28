package domain.donaciones;

import domain.heladera.Heladera;
import lombok.Getter;
import lombok.ToString;

@ToString(callSuper = true)
@Getter
public class DonacionHeladera extends Donacion{
    private Heladera unaHeladera;

    public DonacionHeladera(Heladera unaHeladera) {
        super(TipoDonacion.DONA_HELADERA);
        this.unaHeladera = unaHeladera;
    }

    public DonacionHeladera() {
        super(TipoDonacion.DONA_HELADERA);
    }
}
