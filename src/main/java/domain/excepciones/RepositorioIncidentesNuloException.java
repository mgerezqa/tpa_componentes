package domain.excepciones;

public class RepositorioIncidentesNuloException extends RuntimeException {
    public RepositorioIncidentesNuloException(String mensaje) {
        super(mensaje);
    }
}
