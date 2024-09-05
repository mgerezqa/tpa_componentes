package repositorios;

import domain.persona.Persona;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import java.util.List;

public class Repositorio implements WithSimplePersistenceUnit {
    public void guardar(Object data) {
        entityManager().persist(data);
    }

    public List<Persona> buscarTodos() {
        return entityManager()
                .createQuery("from " + Persona.class.getName())
                .getResultList();
    }

}
