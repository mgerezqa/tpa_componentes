package repositorios.repositoriosBDD;

import domain.heladera.Heladera.EstadoHeladera;
import domain.heladera.Heladera.Heladera;
import domain.usuarios.Tecnico;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import lombok.Getter;
import repositorios.Repositorio;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class RepositorioHeladeras extends Repositorio implements WithSimplePersistenceUnit {

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

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

    @SuppressWarnings("unchecked")
    public List<Heladera> obtenerTodasLasHeladerasActivas() {
        return entityManager()
                .createQuery("SELECT h FROM Heladera h WHERE h.estadoHeladera =:estado ", Heladera.class)
                .setParameter("estado", EstadoHeladera.ACTIVA)
                .getResultList();
    }

}
