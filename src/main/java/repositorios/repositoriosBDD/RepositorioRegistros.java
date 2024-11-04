package repositorios.repositoriosBDD;

import domain.donaciones.RegistroDePersonaVulnerable;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

public class RepositorioRegistros implements WithSimplePersistenceUnit {

    public void guardar(RegistroDePersonaVulnerable registro){
        entityManager().persist(registro);
    }

    public void eliminarPorId(Long id){
        RegistroDePersonaVulnerable registro = entityManager().find(RegistroDePersonaVulnerable.class,id);
        if(registro != null){
            entityManager().remove(registro);
        }
    }

    public RegistroDePersonaVulnerable obtenerPorId(Long id){
        return entityManager().find(RegistroDePersonaVulnerable.class, id);
    }


}
