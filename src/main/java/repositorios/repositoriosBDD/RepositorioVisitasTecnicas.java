package repositorios.repositoriosBDD;
import domain.donaciones.MantenerHeladera;
import domain.visitas.Visita;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import java.util.List;

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
    public List<Visita> buscarVisitasPorTecnicoId(Long tecnicoId) {
        return entityManager()
                .createQuery(
                        "SELECT v FROM Visita v WHERE v.tecnico.id = :tecnicoId",
                        Visita.class
                )
                .setParameter("tecnicoId", tecnicoId)
                .getResultList();
    }
}
