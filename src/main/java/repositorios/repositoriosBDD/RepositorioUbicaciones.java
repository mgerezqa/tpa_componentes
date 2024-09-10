package repositorios.repositoriosBDD;

import domain.contacto.MedioDeContacto;
import domain.geografia.Ubicacion;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import java.util.List;

public class RepositorioUbicaciones implements WithSimplePersistenceUnit {


    public void guardar(Ubicacion ubi){
        if (entityManager().contains(ubi)) {
            entityManager().merge(ubi);
        } else {
            entityManager().persist(ubi);
        }
    }

    void eliminarPorId(Long id){
        Ubicacion ubi = entityManager().find(Ubicacion.class,id);
        entityManager().remove(ubi);
    }

    public void eliminarTodos() {
        List<Ubicacion> ubicaciones = entityManager()
                .createQuery("SELECT u FROM Ubicacion u", Ubicacion.class)
                .getResultList();

        for (Ubicacion ubicacion : ubicaciones) {
            entityManager().remove(ubicacion);
        }
    }


}
