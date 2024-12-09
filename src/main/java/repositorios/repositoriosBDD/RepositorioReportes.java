package repositorios.repositoriosBDD;

import domain.donaciones.Dinero;
import domain.heladera.Heladera.Heladera;
import domain.reportes.Reporte;
import domain.usuarios.Tecnico;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import repositorios.Repositorio;
import repositorios.interfaces.IRepositorioReportes;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RepositorioReportes extends Repositorio implements WithSimplePersistenceUnit {

    public void guardar(Reporte reporte){
        entityManager().persist(reporte);
    }

    public void eliminarPorId(Long id){
        Reporte reporte = entityManager().find(Reporte.class,id);
        if(reporte != null){
            entityManager().remove(reporte);
        }
    }

    public List<Reporte> obtenerTodos() {
        return entityManager()
                .createQuery("FROM Reporte", Reporte.class)
                .getResultList();
    }

    public Reporte obtenerPorId(Long id){
        return entityManager().find(Reporte.class, id);
    }
}
