package domain.suscripciones;

import domain.heladera.Heladera.Heladera;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_suscripcion")
public abstract class TipoDeSuscripcion {
    @Id
    @GeneratedValue
    private String id;

    @Getter
    @Column(name = "descripcion")
    private String descripcion;
    @Setter @Getter
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
