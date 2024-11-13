package repositorios.repositoriosBDD;
import domain.usuarios.Usuario;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import lombok.Data;
import repositorios.Repositorio;
import java.util.List;
import java.util.Optional;

public class RepositorioUsuarios extends Repositorio implements WithSimplePersistenceUnit {

    @SuppressWarnings("unchecked")
    public List<Usuario> buscarUsuariosCon(String nombre) {
        return entityManager()
                .createQuery("from " + Usuario.class.getName() + " u where u.nombreUsuario = :name")
                .setParameter("name", nombre)
                .getResultList();
    }

    @SuppressWarnings("unchecked")
    public Optional<Usuario> buscarPorNombre(String nombre) {
        return entityManager()
                .createQuery("from " + Usuario.class.getName() + " u where u.nombreUsuario = :name")
                .setParameter("name", nombre)
                .getResultStream()
                .findFirst();
    }

}
