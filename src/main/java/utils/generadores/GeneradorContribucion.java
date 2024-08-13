package utils.generadores;

import domain.contribucionNuevo.*;

public interface GeneradorContribucion {
    static Contribucion generar(TipoContribucion unTipo){
        switch (unTipo){
            case DINERO -> {return new ContribucionDinero();}
            case DISTRIBUCION -> {return new ContribucionDistribucion();}
            case MANTENIMIENTO -> {return new ContribucionMantenimientoHeladera();}
            case REGISTRO_P_V -> {return new ContribucionRegistroPersona();}
            case VIANDA -> {return new ContribucionVianda();}
            default -> throw new RuntimeException("Tipo de contribucion Invalida!");
        }
    }
}
