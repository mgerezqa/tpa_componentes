package domain.tarjeta;

import domain.heladera.Heladera.Heladera;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "registro_uso_tarjeta")
public class RegistroDeUso { //Cuándo la usó, y en cuál heladera.
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    @Column(name = "registro_fecha_uso")
    private LocalDateTime datetime;
    @Transient
    private Heladera heladera;

    public RegistroDeUso(Heladera heladera){
        this.datetime = LocalDateTime.now();
        this.heladera = heladera;
    }
}