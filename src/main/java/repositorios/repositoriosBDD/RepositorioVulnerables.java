package repositorios.repositoriosBDD;

import domain.donaciones.Distribuir;
import domain.persona.PersonaVulnerable;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

public class RepositorioVulnerables implements WithSimplePersistenceUnit {

    public void guardar(PersonaVulnerable vulnerable){
        entityManager().persist(vulnerable);
    }

    public void eliminarPorId(Long id){
        PersonaVulnerable vulnerable = entityManager().find(PersonaVulnerable.class,id);
        if(vulnerable != null){
            entityManager().remove(vulnerable);
        }
    }

    public PersonaVulnerable obtenerPorId(Long id){
        return entityManager().find(PersonaVulnerable.class, id);
    }

}
