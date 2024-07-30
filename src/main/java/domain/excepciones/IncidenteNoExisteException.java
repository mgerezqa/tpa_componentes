package domain.excepciones;

public class IncidenteNoExisteException extends RuntimeException{
    public IncidenteNoExisteException(String mensajeError) {
        super(mensajeError);
    }
}
