package repositorios.repositoriosBDD;

import domain.donaciones.Dinero;
import domain.donaciones.RegistroDePersonaVulnerable;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import repositorios.Repositorio;

import java.util.List;

public class RepositorioRegistrosVulnerables extends Repositorio implements WithSimplePersistenceUnit {

    public void guardar(RegistroDePersonaVulnerable registro){
        entityManager().persist(registro);
    }

    public void eliminarPorId(Long id){
        RegistroDePersonaVulnerable registro = entityManager().find(RegistroDePersonaVulnerable.class, id);
        if (registro != null) {
            entityManager().remove(registro);
        }
    }

    public RegistroDePersonaVulnerable obtenerPorId(Long id){
        return entityManager().find(RegistroDePersonaVulnerable.class, id);
    }

    public List<RegistroDePersonaVulnerable> obtenerTodos() {
        return entityManager()
                .createQuery("FROM RegistroDePersonaVulnerable", RegistroDePersonaVulnerable.class)
                .getResultList();
    }

    // Método para buscar registros de personas vulnerables por colaborador
    public List<RegistroDePersonaVulnerable> buscarRegistrosPorColaborador(Long colaboradorId) {
        return entityManager()
                .createQuery(
                        "SELECT r FROM RegistroDePersonaVulnerable r WHERE r.colaboradorQueLaDono.id = :colaboradorId",
                        RegistroDePersonaVulnerable.class
                )
                .setParameter("colaboradorId", colaboradorId)
                .getResultList();
    }
}
