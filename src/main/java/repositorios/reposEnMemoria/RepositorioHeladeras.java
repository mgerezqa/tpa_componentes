package repositorios.reposEnMemoria;
import domain.heladera.Heladera.Heladera;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import java.util.List;

public class RepositorioHeladeras implements WithSimplePersistenceUnit {

    public Heladera obtenerHeladeraPorID(String id){
        return entityManager().find(Heladera.class, id);
    }

    public void guardar(Heladera heladera) {
        entityManager().persist(heladera);
    }

    public void eliminar(Heladera heladera) {
        entityManager().remove(heladera);
    }

    public List<Heladera> obtenerTodasLasHeladeras() {
        return entityManager()
                .createQuery("from " + Heladera.class.getName())
                .getResultList();
    }


}
