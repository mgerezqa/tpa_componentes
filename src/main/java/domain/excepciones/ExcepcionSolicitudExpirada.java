package domain.excepciones;

public class ExcepcionSolicitudExpirada extends RuntimeException {
    public ExcepcionSolicitudExpirada(String mensajeError) {
        super(mensajeError);
    }
}