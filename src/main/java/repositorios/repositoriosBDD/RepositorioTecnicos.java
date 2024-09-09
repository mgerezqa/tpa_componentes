package repositorios.repositoriosBDD;

import domain.usuarios.Tecnico;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import java.util.List;

public class RepositorioTecnicos implements WithSimplePersistenceUnit {

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

    public void eliminarTodos() {
        List<Tecnico> tecnicos = entityManager()
                .createQuery("SELECT t FROM Tecnico t", Tecnico.class)
                .getResultList();

        for (Tecnico tecnico : tecnicos) {
            entityManager().remove(tecnico);
        }
    }
}
