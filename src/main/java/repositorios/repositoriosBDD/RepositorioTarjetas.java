package repositorios.repositoriosBDD;

import domain.donaciones.Distribuir;
import domain.tarjeta.Tarjeta;
import domain.usuarios.Tecnico;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import repositorios.Repositorio;

import java.util.List;
import java.util.Optional;

public class RepositorioTarjetas extends Repositorio implements WithSimplePersistenceUnit {

    public void guardar(Tarjeta tarjeta){
        entityManager().persist(tarjeta);
    }

    public void eliminarPorUuid(String uuid){
        Tarjeta tarjeta = entityManager().find(Tarjeta.class,uuid);
        if(tarjeta != null){
            entityManager().remove(tarjeta);
        }
    }
    public List<Tarjeta> buscarTarjetas() {
        return entityManager()
                .createQuery("FROM Tarjeta", Tarjeta.class)
                .getResultList();
    }

    public Tarjeta obtenerPorId(String uuid){
        return entityManager().find(Tarjeta.class, uuid);
    }
    public Optional<Tarjeta> obtenerPorUUID(String uuid){
        return Optional.ofNullable(entityManager().find(Tarjeta.class, uuid));
    }
}
