package domain.donaciones;


import domain.vianda.Vianda;
import lombok.Getter;
import lombok.ToString;

@ToString(callSuper = true)
@Getter
public class DonacionVianda extends Donacion {
    private Vianda unaVianda;

    public DonacionVianda(Vianda unaVianda) {
        super(TipoDonacion.DONA_VIANDA);
        this.unaVianda = unaVianda;
    }
    public DonacionVianda(){
        super(TipoDonacion.DONA_VIANDA);
    }
}
