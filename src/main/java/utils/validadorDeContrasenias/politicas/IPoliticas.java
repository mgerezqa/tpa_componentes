package utils.validadorDeContrasenias.politicas;
import domain.usuarios.Usuario;

public interface IPoliticas {
    void chequearPolitica(String nombreUsuario, String contrasenia) throws Exception;
}
