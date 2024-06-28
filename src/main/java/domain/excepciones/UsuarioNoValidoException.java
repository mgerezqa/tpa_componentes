package domain.excepciones;

public class UsuarioNoValidoException extends RuntimeException{
    public UsuarioNoValidoException(String mensajeError) {
        super(mensajeError);
    }
}
