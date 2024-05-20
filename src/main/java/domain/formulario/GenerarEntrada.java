package domain.formulario;

public interface GenerarEntrada {
    public static Entrada generar(TipoCampo tipo){
        switch (tipo){
            case CAMPO_TEXTO:
                return new EntradaTexto();
            case CAMPO_NUMERICO:
                return new EntradaNumerica();
            case CAMPO_FECHA_NACIMIENTO:
                return new EntradaFechaNacimiento();
            case CAMPO_FECHA:
                return new EntradaFecha();
            case CAMPO_MULTIPLE:
                return new EntradaMultiple();
            case CAMPO_NOMBRE:
                return new EntradaNombre();
            case CAMPO_CUIT:
                return new EntradaCuit();

            default:
                return null;
        }
    }
}
