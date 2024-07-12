package domain.suscripciones;

public class SuscripcionFactory {

    public TipoDeSuscripcion crearSuscripcion(eTipoDeSuscripcion tipoSuscripcion) {
        switch (tipoSuscripcion) {
            case POR_CANTIDAD_DE_VIANDAS_DISP:
                return new SuscripcionPorCantidadDeViandasDisp();
            case POR_CANTIDAD_DE_VIANDAS_HASTA_ALC_MAX:
                return new SuscripcionPorCantidadDeViandasHastaAlcMax();
            case POR_DESPERFECTO_H:
                return new SuscripcionPorDesperfectoH();
            default:
                return null;
        }
    }

}
