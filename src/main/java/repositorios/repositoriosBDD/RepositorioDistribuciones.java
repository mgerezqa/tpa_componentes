package repositorios.repositoriosBDD;

import domain.donaciones.Distribuir;
import domain.donaciones.RegistroDePersonaVulnerable;
import domain.donaciones.Vianda;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import java.util.List;

public class RepositorioDistribuciones implements WithSimplePersistenceUnit {

    public void guardar(Distribuir distribucion){
        entityManager().persist(distribucion);
    }

    public void eliminarPorId(Long id){
        Distribuir distribucion = entityManager().find(Distribuir.class,id);
        if(distribucion != null){
            entityManager().remove(distribucion);
        }
    }

    public Distribuir obtenerPorId(Long id){
        return entityManager().find(Distribuir.class, id);
    }
    public List<Distribuir> buscarPorColaboradorId(Long colaboradId) {
        return entityManager()
                .createQuery(
                        "SELECT d FROM Distribuir d WHERE d.colaboradorQueLaDono.id = :colaboradId",
                        Distribuir.class
                )
                .setParameter("colaboradId", colaboradId)
                .getResultList();
    }
}