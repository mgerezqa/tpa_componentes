package domain.incidentes;
import domain.heladera.Heladera.EstadoHeladera;
import domain.heladera.Heladera.Heladera;
import domain.usuarios.Tecnico;
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
@DiscriminatorColumn(name = "tipo")
public abstract class Incidente {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "heladera_id", referencedColumnName = "id", nullable = false)
    private Heladera heladera;

    @Column(name = "fechaYHora", columnDefinition = "DATE")
    private LocalDateTime fechaYHora;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "tecnicoAsig_id", referencedColumnName = "id", nullable = false)
    private Tecnico tecnicoAsignado;

    public Incidente(Heladera heladera) {
        this.heladera = heladera;
        heladera.agregarIncidente(this);                     // Persistir en cada heladera.
        heladera.setEstadoHeladera(EstadoHeladera.INACTIVA); // Setear cada heladera como inactiva.
    }

}
