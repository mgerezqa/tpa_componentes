package repositorios.repositoriosBDD;

import domain.incidentes.Alerta;
import domain.incidentes.FallaTecnica;
import domain.incidentes.Incidente;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import lombok.Data;

import java.util.List;

@Data
public class RepositorioIncidentes implements WithSimplePersistenceUnit {

    public void guardar(Incidente incidente) {
        if (entityManager().contains(incidente)) {
            entityManager().merge(incidente);
        } else {
            entityManager().persist(incidente);
        }
    }

    public void eliminarPorId(String id) {
        Incidente incidente = entityManager().find(Incidente.class, id);
        if (incidente != null) {
            entityManager().remove(incidente);
        }
    }

    public void eliminar(Incidente incidente) {
        if (entityManager().contains(incidente)) {
            entityManager().remove(incidente);
        } else {
            entityManager().remove(entityManager().merge(incidente));
        }
    }

    public Incidente obtenerPorId(String id) {
        return entityManager().find(Incidente.class, id);
    }

    @SuppressWarnings("unchecked")
    public List<Incidente> obtenerTodos() {
        return entityManager()
                .createQuery("FROM Incidente", Incidente.class)
                .getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Incidente> obtenerPorHeladera(Long id) {
        return entityManager()
                .createQuery("SELECT inc FROM Incidente inc WHERE inc.heladera.id = :heladeraId", Incidente.class)
                .setParameter("heladeraId", id)
                .getResultList();
    }

    public void guardarAlerta(Alerta alerta) {
        if (entityManager().contains(alerta)) {
            entityManager().merge(alerta);
        } else {
            entityManager().persist(alerta);
        }
    }

    public void guardarFallaTecnica(FallaTecnica fallaTecnica) {
        if (entityManager().contains(fallaTecnica)) {
            entityManager().merge(fallaTecnica);
        } else {
            entityManager().persist(fallaTecnica);
        }
    }
}
