package domain.tarjeta;

import domain.heladera.Heladera.Heladera;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@NoArgsConstructor
@Table(name = "registro_de_uso")
public class RegistroDeUso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha_uso", nullable = false)
    private LocalDateTime fechaDeUso;

    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST},fetch = FetchType.LAZY)
    @JoinColumn(name = "id_heladera", nullable = false)
    private Heladera heladera; // La heladera donde se usó la tarjeta

    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST},fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tarjeta", nullable = false)
    private Tarjeta tarjeta;  // Relación con la Tarjeta

    public RegistroDeUso(Heladera heladera, Tarjeta tarjeta) {
        this.fechaDeUso = LocalDateTime.now();
        this.heladera = heladera;
        this.tarjeta = tarjeta;
    }
}
