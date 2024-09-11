package domain.suscripciones;

import domain.heladera.Heladera.Heladera;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Getter
@Setter
@NoArgsConstructor
@Entity
@DiscriminatorValue("suscripcion_cantidad_viandas_max")
public class SuscripcionPorCantidadDeViandasHastaAlcMax extends TipoDeSuscripcion {


    @Column(name = "cantidad_viandas_hasta_max")
    private int cantidadDeViandasHastaAlcMax;


    @Column(name = "descripcion")
    public final String descripcion = " tiene " + cantidadDeViandasHastaAlcMax + " viandas hasta alcanzar el máximo.";


    @Override
    public boolean cumpleCriterio(Heladera heladera) {
        return verificarCantidadViandasHastaAlcanzarMaximo(heladera,cantidadDeViandasHastaAlcMax);
    }

    // Verificar si la cantidad de viandas hasta alcanzar el máximo es igual a n
    public boolean verificarCantidadViandasHastaAlcanzarMaximo(Heladera heladera,int n) {
        return cantidadDeViandasHastaAlcanzarMaximo(heladera) <= n;
    }

    //Retorna la cantidad de viandas hasta alcancar el maximo
    public int cantidadDeViandasHastaAlcanzarMaximo(Heladera heladera) {
        return capacidadMaximaDeHeladera(heladera) - capacidadActualHeladera(heladera);
    }

}
