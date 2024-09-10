package domain.suscripciones;

import domain.heladera.Heladera.Heladera;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Data
@NoArgsConstructor
@Entity
@DiscriminatorValue("suscripcion_cantidad_viandas_disp")
public class SuscripcionPorCantidadDeViandasDisponibles extends TipoDeSuscripcion {

    @Setter @Getter
    @Column(name = "cantidad_viandas_disponibles")
    private int cantidadDeViandasDisp;

    @Getter
    @Column(name = "descripcion")
    public final String descripcion = " tiene " + cantidadDeViandasDisp + " viandas disponibles.";


    @Override

    public boolean cumpleCriterio(Heladera heladera) {
        return verificarCantidadViandasDisponibles(cantidadDeViandasDisp,heladera);
    }

    public boolean verificarCantidadViandasDisponibles(int n,Heladera heladera)
    {
        return capacidadActualHeladera(heladera) <= n;
    }




}
