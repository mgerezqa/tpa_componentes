package repositorios.repositoriosBDD;
import domain.usuarios.Colaborador;
import domain.usuarios.ColaboradorFisico;
import domain.usuarios.ColaboradorJuridico;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import lombok.Data;
import repositorios.Repositorio;

import java.util.List;

@Data

public class RepositorioColaboradores extends Repositorio implements WithSimplePersistenceUnit {

    public void guardar(Colaborador colaborador){
        entityManager().persist(colaborador);
    }

    public void eliminarPorId(String id){
        Colaborador colaborador = entityManager().find(Colaborador.class, id);
        if(colaborador != null){
            entityManager().remove(colaborador);
        }
    }

    public void eliminar(Colaborador colaborador) {
        entityManager().remove(colaborador);
    }

    public Colaborador obtenerPorId(String id){
        return entityManager().find(Colaborador.class, id);
    }

    @SuppressWarnings("unchecked")
    public List<Colaborador> colaboradoresActivos(){
        return entityManager()
                .createQuery("FROM Colaborador c WHERE c.activo = true", Colaborador.class)
                .getResultList();
    }
    public List<ColaboradorFisico> colaboradoreFisicosActivos(){
        return entityManager()
                .createQuery("FROM ColaboradorFisico c WHERE c.activo = true", ColaboradorFisico.class)
                .getResultList();
    }
}
