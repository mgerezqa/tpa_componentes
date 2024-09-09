package domain.donaciones;

import domain.heladera.Heladera.Heladera;
import domain.usuarios.ColaboradorJuridico;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "donaciones_mantener_heladera")
@NoArgsConstructor // Constructor sin argumentos requerido por JPA
public class MantenerHeladera {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "heladera_id", nullable = false)
    private Heladera heladera;

    @Column(name = "fecha_donacion", nullable = false)
    private LocalDate fechaDeDonacion;

    @ManyToOne
    @JoinColumn(name = "colaborador_id", nullable = false)
    @Getter
    private ColaboradorJuridico colaboradorQueLaDono;

    @Getter
    @Setter
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