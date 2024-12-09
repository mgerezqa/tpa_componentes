package repositorios.repositoriosBDD;

import domain.donaciones.Dinero;
import domain.donaciones.Distribuir;
import domain.donaciones.MantenerHeladera;
import domain.donaciones.Vianda;
import domain.heladera.Heladera.Heladera;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import repositorios.Repositorio;

import java.util.List;

public class RepositorioMantenciones extends Repositorio implements WithSimplePersistenceUnit {

    public void guardar(MantenerHeladera mantencion){
        entityManager().persist(mantencion);
    }

    public void eliminarPorId(Long id){
        MantenerHeladera mantencion = entityManager().find(MantenerHeladera.class,id);
        if(mantencion != null){
            entityManager().remove(mantencion);
        }
    }

    public MantenerHeladera obtenerPorId(Long id){
        return entityManager().find(MantenerHeladera.class, id);
    }

    public List<MantenerHeladera> obtenerTodas() {
        return entityManager()
                .createQuery("FROM MantenerHeladera ", MantenerHeladera.class)
                .getResultList();
    }

    public List<MantenerHeladera> buscarPorColaboradorId(Long colaboradId) {
        return entityManager()
                .createQuery(
                        "SELECT m FROM MantenerHeladera m WHERE m.colaboradorQueLaDono.id = :colaboradId",
                        MantenerHeladera.class
                )
                .setParameter("colaboradId", colaboradId)
                .getResultList();
    }
}
