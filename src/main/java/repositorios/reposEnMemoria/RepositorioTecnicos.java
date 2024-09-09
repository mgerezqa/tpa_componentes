package repositorios.reposEnMemoria;
import domain.usuarios.Colaborador;
import domain.usuarios.Tecnico;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import repositorios.interfaces.IRepositorioTecnicos;

import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class RepositorioTecnicos implements WithSimplePersistenceUnit {

    public void guardar(Tecnico tecnico){
        entityManager().persist(tecnico);
    }

    public void eliminar(Tecnico tecnico){
        entityManager().remove(tecnico);
    }

    public void eliminarPorId(Long id){
        Tecnico tecnico = entityManager().find(Tecnico.class, id);
        if(tecnico != null){
            entityManager().remove(tecnico);
        }
    }

    @SuppressWarnings("unchecked")
    public List<Tecnico> tecnicosActivos(){
        return entityManager()
                .createQuery("FROM Tecnico tec WHERE tec.activo = true" + Tecnico.class)
                .getResultList();
    }

}
