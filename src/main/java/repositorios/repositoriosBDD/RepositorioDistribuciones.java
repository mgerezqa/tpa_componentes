package repositorios.repositoriosBDD;

import domain.donaciones.Distribuir;
import domain.donaciones.Vianda;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

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
}