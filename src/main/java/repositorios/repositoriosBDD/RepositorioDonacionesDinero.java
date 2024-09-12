package repositorios.repositoriosBDD;

import domain.donaciones.Dinero;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

public class RepositorioDonacionesDinero implements WithSimplePersistenceUnit {

    public void guardar(Dinero donacionDinero){
        entityManager().persist(donacionDinero);
    }

    public void eliminarPorId(Long id){
        Dinero donacionDinero = entityManager().find(Dinero.class,id);
        if(donacionDinero != null){
            entityManager().remove(donacionDinero);
        }
    }

    public Dinero obtenerPorId(Long id){
        return entityManager().find(Dinero.class, id);
    }
}
