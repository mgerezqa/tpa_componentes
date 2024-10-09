package repositorios;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import org.hibernate.engine.spi.PersistenceContext;
import org.hibernate.internal.SessionImpl;

import java.util.List;
import java.util.Optional;

public class Repositorio implements WithSimplePersistenceUnit {
    public void guardar(Object data) {
        entityManager().persist(data);
    }
    public void actualizar(Object data){
        entityManager().merge(data);
    }
    public List<Object> buscarTodos(Class clase) {
        return entityManager()
                .createQuery("from " + clase.getName())
                .getResultList();

    }
    public Optional<Object> buscarPorID(Class clase, Long id){
        return Optional.ofNullable(entityManager().find(clase, id));
    }

}
