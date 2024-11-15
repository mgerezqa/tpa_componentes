package repositorios.repositoriosBDD;

import domain.geografia.Barrio;
import domain.geografia.Localidad;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import repositorios.Repositorio;

import java.util.Optional;

public class RepositorioBarrios extends Repositorio implements WithSimplePersistenceUnit {
    @SuppressWarnings("unchecked")
    public Barrio buscarBarrioPorNombre(String barrio) {
        Optional<Barrio> posibleBarrio = entityManager()
                .createQuery("SELECT b FROM Barrio b WHERE b.barrio = :barrio", Barrio.class)
                .setParameter("barrio", barrio)
                .getResultStream()
                .findFirst();

        if (posibleBarrio.isPresent()) {
            return posibleBarrio.get();
        }else{
            Barrio barrioNuevo = new Barrio(barrio);
            withTransaction(()->{
                this.guardar(barrioNuevo);
            });
            return barrioNuevo;
        }
    }
}
