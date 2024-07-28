package domain.suscripciones;

import domain.heladera.Heladera.Heladera;
import lombok.Getter;
import lombok.Setter;

public class SuscripcionPorCantidadDeViandasHastaAlcMax extends TipoDeSuscripcion {

    @Getter @Setter
    private int cantidadDeViandasHastaAlcMax;

    @Getter
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
