package repositorios.repositoriosBDD;

import domain.heladera.Heladera.Heladera;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import java.util.List;

public class RepositorioHeladeras implements WithSimplePersistenceUnit {

    public Heladera obtenerHeladeraPorID(String id) {
        return entityManager().find(Heladera.class, id);
    }

    public void guardar(Heladera heladera) {
        if (entityManager().contains(heladera)) {
            entityManager().merge(heladera);
        } else {
            entityManager().persist(heladera);
        }
    }

    public void eliminar(Heladera heladera) {
        if (entityManager().contains(heladera)) {
            entityManager().remove(heladera);
        } else {
            entityManager().remove(entityManager().merge(heladera));
        }
    }

    public List<Heladera> obtenerTodasLasHeladeras() {
        return entityManager()
                .createQuery("FROM Heladera", Heladera.class)
                .getResultList();
    }
}
