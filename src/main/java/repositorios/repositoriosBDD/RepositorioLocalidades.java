package repositorios.repositoriosBDD;

import domain.geografia.Localidad;
import domain.geografia.Provincia;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import repositorios.Repositorio;

import java.util.Optional;

public class RepositorioLocalidades extends Repositorio implements WithSimplePersistenceUnit {
    @SuppressWarnings("unchecked")
    public Localidad buscarLocalidad(String localidad) {
        Optional<Localidad> posibleLocalidad = entityManager()
                .createQuery("SELECT l FROM Localidad l WHERE l.localidad = :localidad", Localidad.class)
                .setParameter("localidad", localidad)
                .getResultStream()
                .findFirst();

        if (posibleLocalidad.isPresent()) {
            return posibleLocalidad.get();
        }else{
            Localidad localidadNueva = new Localidad(localidad);
            withTransaction(()->{
                this.guardar(localidadNueva);
            });
            return localidadNueva;
        }
    }
}
