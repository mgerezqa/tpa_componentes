package utils.generadores;

import domain.formularioNuevo.FormularioNuevo;
import domain.formularioNuevo.TipoEntrada;
import domain.formularioNuevo.TipoFormulario;

public interface GeneradorFormulario {
    static FormularioNuevo colabFisico(){
        FormularioNuevo aux = new FormularioNuevo(TipoFormulario.COLABORADOR_FISICO);
        aux.agregarRegistro(TipoEntrada.ENTRADA_TEXTO, "CAMPO_NOMBRE", "Nombre");
        aux.agregarRegistro(TipoEntrada.ENTRADA_TEXTO, "CAMPO_APELLIDO", "Apellido");
        aux.agregarRegistro(TipoEntrada.ENTRADA_SELECCION, "CAMPO_TIPO_DOCUMENTO", "Tipo de documento");
        aux.agregarRegistro(TipoEntrada.ENTRADA_NUMERICA, "CAMPO_NRO_DOCUMENTO", "Numero de documento");
        aux.agregarRegistro(TipoEntrada.ENTRADA_MULTIPLE, "CAMPO_EMAIL", "Correo");
        aux.agregarRegistro(TipoEntrada.ENTRADA_MULTIPLE, "CAMPO_WHATSAPP", "Whatsapp");
        aux.agregarRegistro(TipoEntrada.ENTRADA_MULTIPLE, "CAMPO_TELEGRAM", "Telegram");
        aux.agregarRegistro(TipoEntrada.ENTRADA_MULTIPLE, "CAMPO_FORMA_CONTRIBUCION", "Contribucion a realizar");
        aux.agregarRegistro(TipoEntrada.ENTRADA_FECHA, "CAMPO_FECHA_NACIMIENTO", "Fecha de Nacimiento");
        aux.agregarRegistro(TipoEntrada.ENTRADA_TEXTO, "CAMPO_DIRECCION", "Direccion");

        return aux;
    }

    static FormularioNuevo colabJuridico(){
        FormularioNuevo aux = new FormularioNuevo(TipoFormulario.COLABORADOR_JURIDICO);
        aux.agregarRegistro(TipoEntrada.ENTRADA_TEXTO, "CAMPO_RAZON_SOCIAL", "Razon social");
        aux.agregarRegistro(TipoEntrada.ENTRADA_SELECCION, "CAMPO_TIPO_JURIDICO", "Tipo de razon social");
        aux.agregarRegistro(TipoEntrada.ENTRADA_SELECCION, "CAMPO_RUBRO", "Rubro");
        aux.agregarRegistro(TipoEntrada.ENTRADA_MULTIPLE, "CAMPO_EMAIL", "Correo");
        aux.agregarRegistro(TipoEntrada.ENTRADA_MULTIPLE, "CAMPO_WHATSAPP", "Whatsapp");
        aux.agregarRegistro(TipoEntrada.ENTRADA_MULTIPLE, "CAMPO_TELEGRAM", "Telegram");
        aux.agregarRegistro(TipoEntrada.ENTRADA_MULTIPLE, "CAMPO_FORMA_CONTRIBUCION", "Contribucion a realizar");
        aux.agregarRegistro(TipoEntrada.ENTRADA_TEXTO, "CAMPO_DIRECCION", "Direccion");
        return aux;
    }
}
