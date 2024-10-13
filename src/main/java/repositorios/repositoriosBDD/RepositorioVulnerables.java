package repositorios.repositoriosBDD;

import domain.donaciones.Distribuir;
import domain.persona.PersonaVulnerable;
import domain.usuarios.Colaborador;
import domain.usuarios.ColaboradorJuridico;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import repositorios.Repositorio;

import java.util.List;

public class RepositorioVulnerables extends Repositorio implements WithSimplePersistenceUnit {

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

    public void eliminar(PersonaVulnerable personaVulnerable) {
        entityManager().remove(personaVulnerable);
    }

    public List<PersonaVulnerable> obtenerPersonasVulnerables(){
        return entityManager()
                .createQuery("FROM PersonaVulnerable ", PersonaVulnerable.class)
                .getResultList();
    }

}
