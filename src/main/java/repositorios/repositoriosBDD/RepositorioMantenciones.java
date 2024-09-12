package repositorios.repositoriosBDD;

import domain.donaciones.MantenerHeladera;
import domain.donaciones.Vianda;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

public class RepositorioMantenciones implements WithSimplePersistenceUnit {

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
}
