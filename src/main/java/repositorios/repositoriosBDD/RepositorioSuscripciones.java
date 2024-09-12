package repositorios.repositoriosBDD;

import domain.donaciones.RegistroDePersonaVulnerable;
import domain.suscripciones.Suscripcion;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

public class RepositorioSuscripciones implements WithSimplePersistenceUnit {

    public void guardar(Suscripcion sub){
        entityManager().persist(sub);
    }

    public void eliminarPorId(Long id){
        Suscripcion sub = entityManager().find(Suscripcion.class,id);
        if(sub != null){
            entityManager().remove(sub);
        }
    }

    public Suscripcion obtenerPorId(Long id){
        return entityManager().find(Suscripcion.class, id);
    }
}
