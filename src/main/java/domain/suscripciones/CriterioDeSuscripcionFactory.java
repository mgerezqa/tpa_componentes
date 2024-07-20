package domain.suscripciones;

public class CriterioDeSuscripcionFactory {

    public CriterioDeSuscripcion crearSuscripcion(eCriterioDeSuscripcion tipoSuscripcion) {
        switch (tipoSuscripcion) {
            case POR_CANTIDAD_DE_VIANDAS_DISP:
                return new CriterioPorCantidadDeViandasDisponibles();
            case POR_CANTIDAD_DE_VIANDAS_HASTA_ALC_MAX:
                return new CriterioPorCantidadDeViandasHastaAlcMax();
            case POR_DESPERFECTO_H:
                return new CriterioPorDesperfectoH();
            default:
                return null;
        }
    }

}
