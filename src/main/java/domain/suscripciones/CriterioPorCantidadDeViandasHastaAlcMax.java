package domain.suscripciones;

import domain.heladera.Heladera.Heladera;
import lombok.Getter;
import lombok.Setter;

public class CriterioPorCantidadDeViandasHastaAlcMax extends CriterioDeSuscripcion {

    @Getter @Setter
    private int cantidadDeViandasHastaAlcMax;

    private final String descripcion = "CANTIDAD_DE_VIANDAS_HASTA_ALCANZAR_MAX";


    @Override
    public boolean verificarCondicion(Heladera heladera) {
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
