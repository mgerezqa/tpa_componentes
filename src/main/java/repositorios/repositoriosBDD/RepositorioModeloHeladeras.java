package repositorios.repositoriosBDD;

import domain.heladera.Heladera.ModeloDeHeladera;
import domain.usuarios.Tecnico;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import repositorios.Repositorio;

import java.util.Optional;

public class RepositorioModeloHeladeras extends Repositorio implements WithSimplePersistenceUnit {
    @SuppressWarnings("unchecked")
    public Optional<ModeloDeHeladera> buscarModeloPorNombre(String modelo) {
        return entityManager()
                .createQuery("SELECT m FROM ModeloDeHeladera m WHERE m.nombreModelo = :modelo", ModeloDeHeladera.class)
                .setParameter("modelo", modelo)
                .getResultStream()
                .findFirst();
    }
}
