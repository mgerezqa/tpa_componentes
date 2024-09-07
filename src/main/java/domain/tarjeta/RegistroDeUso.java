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
@Table(name = "registro_uso_tarjeta")
public class RegistroDeUso { //Cuándo la usó, y en cuál heladera.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "registro_fecha_uso")
    private LocalDateTime datetime;
    @Transient
    private Heladera heladera;

    public RegistroDeUso(){
        this.datetime = LocalDateTime.now();
    }
}