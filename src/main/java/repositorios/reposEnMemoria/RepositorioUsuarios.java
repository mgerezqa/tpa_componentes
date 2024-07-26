package repositorios.reposEnMemoria;
import domain.usuarios.Usuario;
import lombok.Data;
import repositorios.interfaces.IRepositorioUsuarios;

@Data
public class RepositorioUsuarios implements IRepositorioUsuarios {
    //TODO
    @Override
    public void agregarUsuario(Usuario usuario) {

    }
    //TODO
    @Override
    public Usuario buscarUsuario(Usuario usuario) {
        return null;
    }
    //TODO
    @Override
    public Usuario buscarUsuarioPorNombre(String nombreUsuario) {
        return null;
    }
}
