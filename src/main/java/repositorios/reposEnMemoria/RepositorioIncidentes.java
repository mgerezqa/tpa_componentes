package repositorios.reposEnMemoria;
import domain.heladera.Heladera.Heladera;
import domain.incidentes.Incidente;
import domain.usuarios.Colaborador;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import lombok.Data;
import repositorios.interfaces.IRepositorioIncidentes;

import java.util.ArrayList;
import java.util.List;

@Data
public class RepositorioIncidentes implements WithSimplePersistenceUnit {

    public void guardar(Incidente incidente){
        entityManager().persist(incidente);
    }

    public void eliminarPorId(String id){
        Incidente incidente = entityManager().find(Incidente.class, id);
        if(incidente != null){
            entityManager().remove(incidente);
        }
    }

    public void eliminar(Incidente incidente) {
        entityManager().remove(incidente);
    }

    public Incidente obtenerPorId(String id){
        return entityManager().find(Incidente.class,id);
    }

    @SuppressWarnings("unchecked")
    public List<Incidente> obtenerTodos(){
        return entityManager()
                .createQuery("from" + Incidente.class.getName())
                .getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Incidente> obtenerPorHeladera(Long id){
        return entityManager()
                .createQuery("SELECT inc FROM Incidente inc WHERE inc.heladera.id = :heladeraId")
                .getResultList();
    }


}
