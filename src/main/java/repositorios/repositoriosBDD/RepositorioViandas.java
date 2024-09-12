package repositorios.repositoriosBDD;

import domain.donaciones.Vianda;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

public class RepositorioViandas implements WithSimplePersistenceUnit {

    public void guardar(Vianda vianda){
        entityManager().persist(vianda);
    }

    public void eliminarPorId(Long id){
        Vianda vianda = entityManager().find(Vianda.class,id);
        if(vianda != null){
            entityManager().remove(vianda);
        }
    }

    public Vianda obtenerPorId(Long id){
        return entityManager().find(Vianda.class, id);
    }
}
