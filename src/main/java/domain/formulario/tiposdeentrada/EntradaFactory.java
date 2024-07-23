package domain.formulario.tiposdeentrada;

import domain.formulario.eTipoCampo;

public interface EntradaFactory {
    public static Entrada crear(eTipoCampo tipo){
        switch (tipo){
            case CAMPO_TEXTO:
                return new EntradaTexto();
            case CAMPO_NUMERICO:
                return new EntradaNumerica();
            case CAMPO_FECHA:
                return new EntradaFecha();
            case CAMPO_MULTIPLE:
                return new EntradaMultiple();
            case CAMPO_NOMBRE:
                return new EntradaNombre();
            case CAMPO_CUIT:
                return new EntradaCuit();
            case CAMPO_TELEFONO:
                return new EntradaTelefono();
            case CAMPO_EMAIL:
                return new EntradaEmail();

            default:
                return null;
        }
    }
}
