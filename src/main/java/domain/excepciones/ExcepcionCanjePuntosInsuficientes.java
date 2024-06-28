package domain.excepciones;

public class ExcepcionCanjePuntosInsuficientes extends RuntimeException {
    public ExcepcionCanjePuntosInsuficientes(String mensajeError) {
        super(mensajeError);
    }
}