package repositorios.repositoriosBDD;
import domain.visitas.Visita;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

public class RepositorioVisitasTecnicas implements WithSimplePersistenceUnit {

    public void guardar(Visita visita) {
        entityManager().persist(visita);
    }

    public void eliminar(Visita visita){
        entityManager().remove(visita);
    }

    public Visita buscarVisitaPorId(Long id) {
        return entityManager().find(Visita.class, id);
    }
}
