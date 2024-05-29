package domain.donaciones;

import domain.heladera.Heladera.Heladera;
import domain.usuarios.ColaboradorJuridico;
import lombok.Getter;

import java.time.LocalDate;

public class MantenerHeladera extends Contribucion {

    private Heladera heladera;
    private LocalDate fechaDeDonacion;

    public MantenerHeladera() {
        super(TipoContribucion.HELADERA);
    }

    public MantenerHeladera(Heladera heladera, LocalDate fechaDeDonacion) {
        super(TipoContribucion.HELADERA);
        this.heladera = heladera;
        this.fechaDeDonacion = fechaDeDonacion;
    }


}
