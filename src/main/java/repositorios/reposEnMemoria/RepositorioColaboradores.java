package repositorios.reposEnMemoria;
import domain.heladera.Heladera.Heladera;
import domain.puntos.CategoriaOferta;
import domain.usuarios.Colaborador;
import domain.usuarios.ColaboradorFisico;
import domain.usuarios.Tecnico;
import domain.usuarios.Usuario;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import lombok.Data;
import repositorios.interfaces.IRepositorioColaboradores;
import repositorios.interfaces.IRepositorioUsuarios;

import java.util.List;
import java.util.Optional;

@Data
public class RepositorioColaboradores implements WithSimplePersistenceUnit {

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
                .createQuery("FROM Colaborador c WHERE c.activo = true" + Colaborador.class)
                .getResultList();
    }


}
