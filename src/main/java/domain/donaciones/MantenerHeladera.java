package domain.donaciones;

import domain.heladera.Heladera;
import domain.usuarios.Colaborador;
import domain.usuarios.ColaboradorJuridico;
import lombok.Getter;

import java.time.LocalDate;

public class MantenerHeladera {

    private Heladera heladera;
    private LocalDate fechaDeDonacion;
    @Getter
    private Colaborador colaboradorQueLaDono;

    public MantenerHeladera(Heladera heladera, LocalDate fechaDeDonacion, ColaboradorJuridico colaboradorQueLaDono) {
        this.heladera = heladera;
        this.fechaDeDonacion = fechaDeDonacion;
        this.colaboradorQueLaDono = colaboradorQueLaDono;
    }


}
