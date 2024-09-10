package domain.suscripciones;

import domain.heladera.Heladera.Heladera;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Data
@NoArgsConstructor
@Entity
@DiscriminatorValue("suscripcion_desperfecto_heladera")
public class SuscripcionPorDesperfectoH extends TipoDeSuscripcion {

    @Getter
    @Column(name = "descripcion")
    public final String descripcion = " tiene un desperfecto.";

    @Override
    public boolean cumpleCriterio(Heladera heladera) {
        return verificarDesperfecto(heladera);
    }

    public boolean verificarDesperfecto(Heladera heladera) {
        return !heladera.estaActivaHeladera();
    }
}
