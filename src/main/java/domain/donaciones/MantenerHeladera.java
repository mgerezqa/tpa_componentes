package domain.donaciones;

import domain.heladera.Heladera.Heladera;
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
public class MantenerHeladera {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "heladera_id", nullable = false)
    private Heladera heladera;

    @Column(name = "fecha_comienzo", nullable = false)
    private LocalDate fechaDeDonacion;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "colaborador_id", nullable = false)
    private ColaboradorJuridico colaboradorQueLaDono;

    @Column(name = "meses_puntarizados", nullable = false)
    private Integer mesesPuntarizados = 0;

    public MantenerHeladera(Heladera heladera, LocalDate fechaDeDonacion, ColaboradorJuridico colaboradorQueLaDono) {
        this.heladera = heladera;
        this.fechaDeDonacion = fechaDeDonacion;
        this.colaboradorQueLaDono = colaboradorQueLaDono;
    }

    public int mesesMantenida() {
        int months = (int) ChronoUnit.MONTHS.between(this.fechaDeDonacion, LocalDate.now());
        return months;
    }
}