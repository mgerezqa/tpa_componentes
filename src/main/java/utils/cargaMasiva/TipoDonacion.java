package utils.cargaMasiva;

public enum TipoDonacion {
    DINERO,
    VIANDA,
    DISTRIBUCION,
    MANTENIMIENTO,
    REGISTRO_PV;

    public static TipoDonacion obtenerTipoDonacion (String string){
        try {
            return TipoDonacion.valueOf(string);
        }
        catch (IllegalArgumentException e ){
            throw new RuntimeException("Tipo de donacion INVALIDO");
        }
    }
}
