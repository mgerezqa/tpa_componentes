package repositorios.repositoriosBDD;

import domain.donaciones.Vianda;
import domain.usuarios.ColaboradorFisico;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import repositorios.Repositorio;

import java.util.List;

public class RepositorioViandas extends Repositorio implements WithSimplePersistenceUnit {

    public void guardar(Vianda vianda){
        entityManager().persist(vianda);
    }

    public void eliminarPorId(Long id){
        Vianda vianda = entityManager().find(Vianda.class,id);
        if(vianda != null){
            entityManager().remove(vianda);
        }
    }

    public Vianda obtenerPorId(Long id){
        return entityManager().find(Vianda.class, id);
    }

    public List<Vianda> buscarPorColaboradorId(Long colaboradorId) {
        return entityManager()
                .createQuery("FROM Vianda v WHERE v.colaboradorQueLaDono.id = :colaboradorId", Vianda.class)
                .setParameter("colaboradorId", colaboradorId)
                .getResultList();
    }

    public List<Vianda> obtenerViandas(){
        return entityManager()
                .createQuery("FROM Vianda", Vianda.class)
                .getResultList();
    }

    // Método para buscar todas las donaciones por colaborador
    public List<Vianda> buscarDonacionesPorColaborador(Long colaboradorId) {
        return entityManager()
                .createQuery("SELECT v FROM Vianda v WHERE v.colaboradorQueLaDono.id = :colaboradorId", Vianda.class)
                .setParameter("colaboradorId", colaboradorId)
                .getResultList();
    }
}
