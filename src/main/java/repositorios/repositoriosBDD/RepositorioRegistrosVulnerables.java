package repositorios.repositoriosBDD;

import domain.donaciones.RegistroDePersonaVulnerable;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import java.util.List;
import java.util.stream.Stream;

public class RepositorioRegistrosVulnerables implements WithSimplePersistenceUnit {

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

    public List<RegistroDePersonaVulnerable> buscarPorColaboradorId(Long colaboradId) {
        return entityManager()
                .createQuery(
                        "SELECT r FROM RegistroDePersonaVulnerable r WHERE r.colaboradorQueLaDono.id = :colaboradId",
                        RegistroDePersonaVulnerable.class
                )
                .setParameter("colaboradId", colaboradId)
                .getResultList();
    }

}
