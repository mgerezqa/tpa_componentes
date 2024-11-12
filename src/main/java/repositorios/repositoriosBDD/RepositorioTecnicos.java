package repositorios.repositoriosBDD;

import domain.usuarios.Rol;
import domain.usuarios.Tecnico;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import repositorios.Repositorio;

import java.util.List;
import java.util.Optional;

public class RepositorioTecnicos extends Repositorio implements WithSimplePersistenceUnit {

    public void guardar(Tecnico tecnico) {
        if (entityManager().contains(tecnico)) {
            entityManager().merge(tecnico);
        } else {
            entityManager().persist(tecnico);
        }
    }

    public void eliminar(Tecnico tecnico) {
        if (entityManager().contains(tecnico)) {
            entityManager().remove(tecnico);
        } else {
            entityManager().remove(entityManager().merge(tecnico));
        }
    }

    public void eliminarPorId(Long id) {
        Tecnico tecnico = entityManager().find(Tecnico.class, id);
        if (tecnico != null) {
            entityManager().remove(tecnico);
        }
    }

    @SuppressWarnings("unchecked")
    public List<Tecnico> tecnicosActivos() {
        return entityManager()
                .createQuery("FROM Tecnico tec WHERE tec.activo = true", Tecnico.class)
                .getResultList();
    }
    public List<Tecnico> buscarTodosTecnicos() {
        return entityManager()
                .createQuery("FROM Tecnico", Tecnico.class)
                .getResultList();
    }


    public void eliminarTodos() {
        List<Tecnico> tecnicos = entityManager()
                .createQuery("SELECT t FROM Tecnico t", Tecnico.class)
                .getResultList();

        for (Tecnico tecnico : tecnicos) {
            entityManager().remove(tecnico);
        }
    }
    @SuppressWarnings("unchecked")
    public Optional<Tecnico> buscarTecPorIdUsuario( Long idUsuario ) {
        String sql = "SELECT t.* " +
                "FROM tecnico t " +
                "WHERE t.usuario_id = ?";

        return (Optional<Tecnico>) entityManager()
                .createNativeQuery(sql, Tecnico.class)
                .setParameter(1, idUsuario)
                .getSingleResult();
    }
}
