package utils.generadores;

import domain.formularioNuevo.FormularioNuevo;
import domain.formularioNuevo.TipoCampo;
import domain.formularioNuevo.TipoEntrada;
import domain.formularioNuevo.TipoFormulario;

public interface GeneradorFormulario {
    static FormularioNuevo colabFisico(){
        FormularioNuevo aux = new FormularioNuevo(TipoFormulario.COLABORADOR_FISICO);
        aux.agregarCampo(TipoEntrada.ENTRADA_TEXTO, TipoCampo.CAMPO_NOMBRE, "Nombre");
        aux.agregarCampo(TipoEntrada.ENTRADA_TEXTO, TipoCampo.CAMPO_APELLIDO, "Apellido");
        aux.agregarCampo(TipoEntrada.ENTRADA_SELECCION, TipoCampo.CAMPO_TIPO_DOCUMENTO, "Tipo de documento");
        aux.agregarCampo(TipoEntrada.ENTRADA_NUMERICA, TipoCampo.CAMPO_NRO_DOCUMENTO, "Numero de documento");
        aux.agregarCampo(TipoEntrada.ENTRADA_MULTIPLE, TipoCampo.CAMPO_EMAIL, "Correo");
        aux.agregarCampo(TipoEntrada.ENTRADA_MULTIPLE, TipoCampo.CAMPO_WHATSAPP, "Whatsapp");
        aux.agregarCampo(TipoEntrada.ENTRADA_MULTIPLE, TipoCampo.CAMPO_TELEGRAM, "Telegram");
        aux.agregarCampo(TipoEntrada.ENTRADA_MULTIPLE, TipoCampo.CAMPO_FORMA_CONTRIBUCION, "Contribucion a realizar");
        aux.agregarCampo(TipoEntrada.ENTRADA_FECHA, TipoCampo.CAMPO_FECHA_NACIMIENTO, "Fecha de Nacimiento");
        aux.agregarCampo(TipoEntrada.ENTRADA_TEXTO, TipoCampo.CAMPO_DIRECCION, "Direccion");

        return aux;
    }

    static FormularioNuevo colabJuridico(){
        FormularioNuevo aux = new FormularioNuevo(TipoFormulario.COLABORADOR_JURIDICO);
        aux.agregarCampo(TipoEntrada.ENTRADA_TEXTO, TipoCampo.CAMPO_RAZON_SOCIAL, "Razon social");
        aux.agregarCampo(TipoEntrada.ENTRADA_SELECCION, TipoCampo.CAMPO_TIPO_JURIDICO, "Tipo de razon social");
        aux.agregarCampo(TipoEntrada.ENTRADA_SELECCION, TipoCampo.CAMPO_RUBRO, "Rubro");
        aux.agregarCampo(TipoEntrada.ENTRADA_MULTIPLE, TipoCampo.CAMPO_EMAIL, "Correo");
        aux.agregarCampo(TipoEntrada.ENTRADA_MULTIPLE, TipoCampo.CAMPO_WHATSAPP, "Whatsapp");
        aux.agregarCampo(TipoEntrada.ENTRADA_MULTIPLE, TipoCampo.CAMPO_TELEGRAM, "Telegram");
        aux.agregarCampo(TipoEntrada.ENTRADA_MULTIPLE, TipoCampo.CAMPO_FORMA_CONTRIBUCION, "Contribucion a realizar");
        aux.agregarCampo(TipoEntrada.ENTRADA_TEXTO, TipoCampo.CAMPO_DIRECCION, "Direccion");
        return aux;
    }
}
