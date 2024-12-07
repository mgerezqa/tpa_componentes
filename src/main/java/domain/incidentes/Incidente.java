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
@DiscriminatorColumn(name = "tipo_incidente")
@Table(name = "incidentes")
public abstract class Incidente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE},fetch = FetchType.LAZY)
    @JoinColumn(name = "heladera_id", referencedColumnName = "id", nullable = false)
    private Heladera heladera;

    @Column(name = "fechaYHora", columnDefinition = "DATETIME")
    private LocalDateTime fechaYHora;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE},fetch = FetchType.LAZY)
    @JoinColumn(name = "tecnicoAsig_id", referencedColumnName = "id")
    private Tecnico tecnicoAsignado;

    @Column(name = "resuelto")
    private Boolean resuelto;
    public Incidente(Heladera heladera) {
        this.heladera = heladera;
        this.resuelto = false;
        heladera.agregarIncidente(this);                     // Persistir en cada heladera.
        heladera.setEstadoHeladera(EstadoHeladera.INACTIVA); // Setear cada heladera como inactiva.
    }
    public void resolverIncidente(){
        this.resuelto = true;
    }
    // Método para obtener la descripción del incidente
    public abstract String obtenerDescripcion();

}
