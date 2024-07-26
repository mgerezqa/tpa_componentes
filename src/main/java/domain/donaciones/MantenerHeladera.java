package domain.donaciones;

import domain.heladera.Heladera.Heladera;
import domain.usuarios.Colaborador;
import domain.usuarios.ColaboradorJuridico;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class MantenerHeladera {

    private Heladera heladera;
    private LocalDate fechaDeDonacion;
    @Getter
    private ColaboradorJuridico colaboradorQueLaDono;
    @Getter @Setter
    Integer mesesPuntarizados = 0;

    public MantenerHeladera(Heladera heladera, LocalDate fechaDeDonacion, ColaboradorJuridico colaboradorQueLaDono) {
        this.heladera = heladera;
        this.fechaDeDonacion = fechaDeDonacion;
        this.colaboradorQueLaDono = colaboradorQueLaDono;
    }

    public int mesesMantenida(){
        int months = (int) ChronoUnit.MONTHS.between(this.fechaDeDonacion, LocalDate.now());
        return months;
    }
}
