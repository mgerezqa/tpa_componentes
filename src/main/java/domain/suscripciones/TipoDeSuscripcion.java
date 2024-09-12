package domain.suscripciones;

import domain.heladera.Heladera.Heladera;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "tipos_de_suscripcion")
@DiscriminatorColumn(name = "tipo")
public abstract class TipoDeSuscripcion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "fecha")
    private LocalDateTime fecha;

    public abstract boolean cumpleCriterio(Heladera heladera);

    public int capacidadActualHeladera(Heladera heladera) {
        return heladera.getCapacidadActual();
    }

    public int capacidadMaximaDeHeladera(Heladera heladera) {
        return heladera.getCapacidadMax();
    }

}
