package domain.contribucionNuevo;

import domain.heladera.Heladera.Heladera;
import domain.usuariosNuevo.ColaboradorJuridico;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Getter @Setter
public class ContribucionMantenimientoHeladera extends Contribucion{
    private Heladera heladera;
    private Integer mesesPuntarizados = 0;

    public ContribucionMantenimientoHeladera(Heladera heladera, LocalDate fechaDeDonacion, ColaboradorJuridico colaboradorQueLaDono) {
        super(TipoContribucion.MANTENIMIENTO);
        this.heladera = heladera;
        this.registrarFechaDeContribucion(fechaDeDonacion);
        this.registrarColaborador(colaboradorQueLaDono);
    }
    public ContribucionMantenimientoHeladera(){
        super(TipoContribucion.MANTENIMIENTO);
    }

    public int mesesMantenida(){
        return (int) ChronoUnit.MONTHS.between(this.getFechaDeContribucion(), LocalDate.now());
    }
}
