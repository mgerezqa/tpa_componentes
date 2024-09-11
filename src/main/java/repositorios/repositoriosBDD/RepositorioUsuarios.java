package repositorios.repositoriosBDD;
import domain.usuarios.Usuario;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import lombok.Data;
import repositorios.interfaces.IRepositorioUsuarios;

@Data
public class RepositorioUsuarios implements WithSimplePersistenceUnit {

    public void agregar(Usuario usuario){
        entityManager().persist(usuario);
    }

}
