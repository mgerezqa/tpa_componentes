package domain.donaciones;

import domain.heladera.Heladera.Heladera;
import domain.usuarios.Colaborador;
import domain.usuarios.ColaboradorJuridico;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "donaciones_mantener_heladera")
public class MantenerHeladera extends Donacion{

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "heladera_id", nullable = false)
    private Heladera heladera;

    @Column(name = "meses_puntarizados", nullable = false)
    private Integer mesesPuntarizados = 0;

    public MantenerHeladera(Heladera heladera, LocalDate fechaDeDonacion, ColaboradorJuridico colaboradorQueLaDono) {
        super(fechaDeDonacion, colaboradorQueLaDono);
        this.heladera = heladera;
    }
    public MantenerHeladera(Heladera heladera, Colaborador colaboradorQueLaDono){
        super(LocalDate.now(), colaboradorQueLaDono);
        this.heladera = heladera;
    }

    public int mesesMantenida() {
        int months = (int) ChronoUnit.MONTHS.between(this.fechaDeDonacion, LocalDate.now());
        return months;
    }
}