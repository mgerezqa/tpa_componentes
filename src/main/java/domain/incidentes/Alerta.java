package domain.incidentes;
import domain.heladera.Heladera.Heladera;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Data
@NoArgsConstructor
@Entity
@DiscriminatorValue("ALERTA")
public class Alerta extends Incidente {

    @Column(name = "tipoAlerta")
    private String tipoAlerta;

    public Alerta(Heladera heladera) {
        super(heladera);
    }
}