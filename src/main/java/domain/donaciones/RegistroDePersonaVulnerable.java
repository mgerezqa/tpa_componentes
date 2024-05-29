package domain.donaciones;


import domain.tarjeta.Tarjeta;
import domain.usuarios.ColaboradorFisico;
import lombok.Getter;

@Getter
public class RegistroDePersonaVulnerable extends Contribucion {
    private Tarjeta tarjeta;

    public RegistroDePersonaVulnerable() {
        super(TipoContribucion.TARJETA);
    }

    public RegistroDePersonaVulnerable(Tarjeta tarjeta) {
        super(TipoContribucion.TARJETA);
        this.tarjeta = tarjeta;
    }

}