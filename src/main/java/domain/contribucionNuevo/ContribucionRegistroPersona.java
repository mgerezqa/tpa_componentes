package domain.contribucionNuevo;

import domain.tarjeta.Tarjeta;
import domain.usuariosNuevo.ColaboradorFisico;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ContribucionRegistroPersona extends Contribucion{
    private Tarjeta tarjeta;

    public ContribucionRegistroPersona(ColaboradorFisico colaborador, Tarjeta tarjeta) {
        super(TipoContribucion.REGISTRO_P_V);
        this.registrarColaborador(colaborador);
        this.tarjeta = tarjeta;
    }
    public ContribucionRegistroPersona(){
        super(TipoContribucion.REGISTRO_P_V);
    }
}
