package repositorios.repositoriosBDD;

import domain.geografia.Provincia;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import repositorios.Repositorio;

import java.util.Optional;

public class RepositorioProvincias extends Repositorio implements WithSimplePersistenceUnit {
    @SuppressWarnings("unchecked")
    public Provincia buscarProvincia(String provincia) {
        Optional<Provincia> posibleProvincia = entityManager()
                .createQuery("SELECT p FROM Provincia p WHERE p.provincia = :provincia", Provincia.class)
                .setParameter("provincia", provincia)
                .getResultStream()
                .findFirst();
        if (posibleProvincia.isPresent()) {
            return posibleProvincia.get();
        }else{
            Provincia provinciaNueva = new Provincia(provincia);
            withTransaction(()->{
                this.guardar(provinciaNueva);
            });
            return provinciaNueva;
        }
    }
}
