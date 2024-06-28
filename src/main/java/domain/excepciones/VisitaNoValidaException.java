package domain.excepciones;

public class VisitaNoValidaException extends RuntimeException{
    public VisitaNoValidaException(String message) {
        super(message);
    }
}
