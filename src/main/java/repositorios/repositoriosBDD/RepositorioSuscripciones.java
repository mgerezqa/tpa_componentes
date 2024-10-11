package repositorios.repositoriosBDD;

import domain.donaciones.RegistroDePersonaVulnerable;
import domain.suscripciones.Suscripcion;
import domain.usuarios.Tecnico;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import repositorios.Repositorio;

import java.util.List;

public class RepositorioSuscripciones extends Repositorio implements WithSimplePersistenceUnit {

    public void guardar(Suscripcion sub){
        entityManager().persist(sub);
    }

    public void eliminarPorId(Long id){
        Suscripcion sub = entityManager().find(Suscripcion.class,id);
        if(sub != null){
            entityManager().remove(sub);
        }
    }
    public List<Suscripcion> buscarTodosSuscripciones() {
        return entityManager()
                .createQuery("FROM Suscripcion", Suscripcion.class)
                .getResultList();
    }

    public Suscripcion obtenerPorId(Long id){
        return entityManager().find(Suscripcion.class, id);
    }
}
