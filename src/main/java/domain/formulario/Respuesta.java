package domain.formulario;

import domain.contacto.Email;
import domain.contacto.Telefono;
import domain.contacto.Whatsapp;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

public class Respuesta {
    @Getter
    private Campo campo;
    @Getter
    private Object valorRespuesta;

    public Respuesta(Campo campo, Object valorRespuesta) {
        this.campo = campo;
        this.valorRespuesta = valorRespuesta;
    }

    // Factory method para crear instancias de Respuesta según el tipo de entrada
    public static Respuesta crearRespuesta(Campo campo, Object valorRespuesta) {
        TipoEntrada tipoEntrada = campo.getTipoEntrada();
        switch (tipoEntrada) {
            case ENTRADA_TEXTO:
                return new RespuestaTexto(campo, (String) valorRespuesta);
            case ENTRADA_NUMERICA:
                return new RespuestaNumerica(campo, (Integer) valorRespuesta);
            case ENTRADA_RESPUESTA_MULTIPLE:
                return new RespuestaMultiple(campo, (List<String>) valorRespuesta);
            case ENTRADA_TIPO_FECHA:
                return new RespuestaFecha(campo,(LocalDate) valorRespuesta);
            case ENTRADA_RESPUESTA_BOOLEANA:
                return new RespuestaBooleana(campo, (Boolean) valorRespuesta);
            case ENTRADA_EMAIL:
                return new RespuestaEmail(campo,(Email) valorRespuesta);
            case ENTRADA_TELEFONO:
                return new RespuestaTelefono(campo,(Telefono) valorRespuesta);
            case ENTRADA_WHATSAPP:
                return new RespuestaWhatsapp(campo,(Whatsapp) valorRespuesta);
            default:
                throw new IllegalArgumentException("Tipo de entrada no válido: " + tipoEntrada);
        }
    }

}

// Subclases específicas de Respuesta según el tipo de entrada
class RespuestaTexto extends Respuesta {
    public RespuestaTexto(Campo campo, String valorRespuesta) {
        super(campo, valorRespuesta);
    }
}

class RespuestaNumerica extends Respuesta {
    public RespuestaNumerica(Campo campo, Integer valorRespuesta) {
        super(campo, valorRespuesta);
    }
}

class RespuestaMultiple extends Respuesta {
    public RespuestaMultiple(Campo campo, List<String> valorRespuesta) {
        super(campo, valorRespuesta);
    }
}

class RespuestaBooleana extends Respuesta {
        public RespuestaBooleana(Campo campo, Boolean valorRespuesta){
            super(campo,valorRespuesta);
        }
}

class RespuestaFecha extends Respuesta{
    public RespuestaFecha(Campo campo, LocalDate valorRespuesta){
        super(campo,valorRespuesta);
    }

}

class RespuestaTelefono extends Respuesta{
    public RespuestaTelefono(Campo campo, Telefono valorRespuesta){
        super(campo,valorRespuesta);
    }
}

class RespuestaWhatsapp extends Respuesta{
    public RespuestaWhatsapp(Campo campo, Whatsapp valorRespuesta){
        super(campo,valorRespuesta);
    }
}

class RespuestaEmail extends Respuesta{
    public RespuestaEmail(Campo campo, Email valorRespuesta){
        super(campo,valorRespuesta);
    }
}