package server.exceptions;

public class CustomEnumConversionException extends RuntimeException{
    public CustomEnumConversionException(String message, Throwable cause) {
        super(message, cause);
    }
}
