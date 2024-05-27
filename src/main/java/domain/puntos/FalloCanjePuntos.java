package domain.puntos;

public class FalloCanjePuntos extends RuntimeException {
    public FalloCanjePuntos(String mensajeError) {
        super(mensajeError);
    }
}