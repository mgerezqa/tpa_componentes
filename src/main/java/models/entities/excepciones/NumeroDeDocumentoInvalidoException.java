package models.entities.excepciones;

public class NumeroDeDocumentoInvalidoException extends RuntimeException {
    public NumeroDeDocumentoInvalidoException(String message) {
        super(message);
    }
}
