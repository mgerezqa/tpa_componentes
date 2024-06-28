package domain.excepciones;

public class CuilInvalidoException extends RuntimeException{
    public CuilInvalidoException(String message) {
        super(message);
    }
}
