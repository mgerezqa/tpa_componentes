package domain.incidentes;
import domain.heladera.Heladera.Heladera;
import domain.incidentes.Incidente;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@DiscriminatorValue("alerta")
public class Alerta extends Incidente {

    @Column(name = "tipoAlerta")
    private String tipoAlerta;

    public Alerta(Heladera heladera) {
        super(heladera);
    }
}
