package domain.excepciones;

public class ExcepcionHeladeraColaboradorNoAutorizado extends RuntimeException {
    public ExcepcionHeladeraColaboradorNoAutorizado(String mensajeError) {
        super(mensajeError);
    }
}