package repositorios.interfaces;
import domain.heladera.Heladera.Heladera;
import domain.incidentes.Incidente;
import java.util.List;

public interface IRepositorioIncidentes {
    void agregarIncidente(Incidente incidente);
    List<Incidente> obtenerTodasLosIncidentes();
    List<Incidente> buscarPorHeladera(Heladera heladera);
    Incidente obtenerIncidentePorId(String id);
}