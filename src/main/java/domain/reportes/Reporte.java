package domain.reportes;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import utils.reportador.Reportador;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "reportes")
@DiscriminatorColumn(name = "tipo_reporte")
public abstract class Reporte {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @Column(name = "fecha_generacion", columnDefinition = "DATETIME")
    protected LocalDateTime fechaGeneracion;

    @Transient
    protected Reportador reportador;

    @Column(name = "pdf")
    protected String pdf;

    public abstract void reportar();
    public abstract String getTipo();
}
