package domain.tarjeta;

import domain.heladera.Heladera.Heladera;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "registro_de_uso")
public class RegistroDeUso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha_uso", nullable = false)
    private LocalDateTime fechaDeUso = LocalDateTime.now();

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE},fetch = FetchType.LAZY)
    @JoinColumn(name = "id_heladera", nullable = false)
    private Heladera heladera; // La heladera donde se usó la tarjeta

    @ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE},fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tarjeta", nullable = false)
    private Tarjeta tarjeta;  // Relación con la Tarjeta

    public RegistroDeUso(LocalDateTime fechaDeUso, Heladera heladera, Tarjeta tarjeta) {
        this.fechaDeUso = fechaDeUso;
        this.heladera = heladera;
        this.tarjeta = tarjeta;
    }


    //Factory methods combinado con el builder, -> OJO que factory method \= simple factory pattern
    public static RegistroDeUso create(Heladera heladeraEncontrada, TarjetaColaborador tarjetaColaborador) {
        RegistroDeUsoBuilder registroDeUsoBuilder = RegistroDeUso.builder();
        registroDeUsoBuilder.heladera(heladeraEncontrada);
        registroDeUsoBuilder.fechaDeUso(LocalDateTime.now());
        registroDeUsoBuilder.tarjeta(tarjetaColaborador);
        return  registroDeUsoBuilder.build();
    }
}
