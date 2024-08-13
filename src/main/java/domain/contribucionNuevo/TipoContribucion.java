package domain.contribucionNuevo;

public enum TipoContribucion {
    DINERO,
    VIANDA,
    DISTRIBUCION,
    MANTENIMIENTO,
    REGISTRO_P_V;

    public static TipoContribucion obtenerEnum(String unString){
        try{
            return TipoContribucion.valueOf(unString);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Tipo de contribucion invalido!");
        }
    }
}
