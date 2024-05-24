package domain.donaciones;


import domain.vianda.Vianda;
import lombok.Getter;

public class DonacionVianda extends Donacion {
    @Getter
    private Vianda unaVianda;

    public DonacionVianda(Vianda unaVianda) {
        super(TipoDonacion.DONA_VIANDA);
        this.unaVianda = unaVianda;
    }
}
