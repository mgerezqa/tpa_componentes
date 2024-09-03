package domain.excepciones;

public class CantidadDisponibleLimitePorDiaException extends RuntimeException {
    public CantidadDisponibleLimitePorDiaException(String msg) {
        super(msg);
    }
}
