package domain.formulario;

public enum TipoCampoFormulario {

    NOMBRE,
    APELLIDO,
    TIPO_DOCUMENTO,
    NRO_DOCUMENTO,
    CONTACTO,
    FORMA_CONTRIBUCION,
    FECHA_NACIMIENTO,
    DIRECCION,
    RAZON_SOCIAL,
    TIPO_JURIDICO,
    RUBRO,
    OTRO;

    public static TipoCampoFormulario obtenerEnum(String unString){
        try{
            return TipoCampoFormulario.valueOf(unString);
        } catch (IllegalArgumentException e) {
            return TipoCampoFormulario.OTRO;
        }
    }
}
