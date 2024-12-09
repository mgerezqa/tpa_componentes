package repositorios.repositoriosBDD;
import domain.donaciones.Dinero;
import domain.donaciones.Distribuir;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import repositorios.Repositorio;
import java.util.List;

public class RepositorioDonacionesReparto extends Repositorio implements WithSimplePersistenceUnit {

    public void eliminarPorId(Long id){
        Distribuir donacionDistribuir = entityManager().find(Distribuir.class,id);
        if(donacionDistribuir != null){
            entityManager().remove(donacionDistribuir);
        }
    }

    public List<Distribuir> obtenerTodos() {
        return entityManager()
                .createQuery("FROM Distribuir ", Distribuir.class)
                .getResultList();
    }

}
