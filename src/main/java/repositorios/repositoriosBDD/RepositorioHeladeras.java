package repositorios.repositoriosBDD;

import domain.heladera.Heladera.EstadoHeladera;
import domain.heladera.Heladera.Heladera;
import domain.usuarios.Tecnico;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import repositorios.Repositorio;

import java.util.List;

public class RepositorioHeladeras extends Repositorio implements WithSimplePersistenceUnit {

    public Heladera obtenerHeladeraPorID(String id) {
        return entityManager().find(Heladera.class, id);
    }

    public String obtenerNombreHeladeraPorID(Long id) {
        return entityManager().find(Heladera.class, id).getNombreIdentificador();
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
