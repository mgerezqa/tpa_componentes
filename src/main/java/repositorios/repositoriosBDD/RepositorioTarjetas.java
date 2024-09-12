package repositorios.repositoriosBDD;

import domain.donaciones.Distribuir;
import domain.tarjeta.Tarjeta;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

public class RepositorioTarjetas implements WithSimplePersistenceUnit {

    public void guardar(Tarjeta tarjeta){
        entityManager().persist(tarjeta);
    }

    public void eliminarPorUuid(String uuid){
        Tarjeta tarjeta = entityManager().find(Tarjeta.class,uuid);
        if(tarjeta != null){
            entityManager().remove(tarjeta);
        }
    }

    public Tarjeta obtenerPorId(String uuid){
        return entityManager().find(Tarjeta.class, uuid);
    }
}
