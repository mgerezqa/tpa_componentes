package domain.formularioNuevo;

public enum TipoCampo {
    //Usado para identificar que representa una respuesta del formulario, orientado al back
    CAMPO_NOMBRE,
    CAMPO_APELLIDO,
    CAMPO_TIPO_DOCUMENTO,
    CAMPO_NRO_DOCUMENTO,
    CAMPO_EMAIL,
    CAMPO_WHATSAPP,
    CAMPO_TELEGRAM,
    CAMPO_FORMA_CONTRIBUCION,
    CAMPO_FECHA_NACIMIENTO,
    CAMPO_DIRECCION,
    CAMPO_RAZON_SOCIAL,
    CAMPO_TIPO_JURIDICO,
    CAMPO_RUBRO,
    CAMPO_OTRO;

    public static TipoCampo obtenerEnum(String unString){
        try{
            return valueOf(unString);
        } catch (IllegalArgumentException e) {
            return TipoCampo.CAMPO_OTRO;
        }
    }
}
