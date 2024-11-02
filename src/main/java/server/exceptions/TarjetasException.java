package server.exceptions;

public class TarjetasException extends RuntimeException {
    public TarjetasException(String message) {
        super(message);
    }

    public TarjetasException(String message, Throwable cause) {
        super(message, cause);
    }
} 