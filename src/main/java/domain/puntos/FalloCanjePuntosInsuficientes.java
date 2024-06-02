package domain.puntos;

public class FalloCanjePuntosInsuficientes extends RuntimeException {
    public FalloCanjePuntosInsuficientes(String mensajeError) {
        super(mensajeError);
    }
}