package domain.usuariosNuevo;

public enum TipoDocumento {
    LIBRETA_CIVICA, // Libreta civica
    LIBRETA_ENROLAMIENTO, // Libreta enrolamiento
    DNI;

    public static TipoDocumento obtenerEnum(String unString){
        try{
            return TipoDocumento.valueOf(unString);
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new RuntimeException("Tipo de documento invalido!");
        }
    }
}
