package repositorios.repositoriosBDD;

import domain.donaciones.Dinero;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import repositorios.Repositorio;

import java.util.List;

public class RepositorioDonacionesDinero extends Repositorio implements WithSimplePersistenceUnit {

    public void guardar(Dinero donacionDinero){
        entityManager().persist(donacionDinero);
    }

    public void eliminarPorId(Long id){
        Dinero donacionDinero = entityManager().find(Dinero.class, id);
        if(donacionDinero != null){
            entityManager().remove(donacionDinero);
        }
    }

    public Dinero obtenerPorId(Long id){
        return entityManager().find(Dinero.class, id);
    }

    public List<Dinero> obtenerTodas() {
        return entityManager()
                .createQuery("FROM Dinero", Dinero.class)
                .getResultList();
    }

    // Método para buscar donaciones de dinero por colaborador
    public List<Dinero> buscarDonacionesPorColaborador(Long colaboradorId) {
        return entityManager()
                .createQuery("SELECT d FROM Dinero d WHERE d.colaboradorQueLaDono.id = :colaboradorId", Dinero.class)
                .setParameter("colaboradorId", colaboradorId)
                .getResultList();
    }
}
