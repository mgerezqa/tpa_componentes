package repositorios.reposEnMemoria;
import domain.heladera.Heladera.Heladera;
import domain.incidentes.Incidente;
import lombok.Data;
import repositorios.interfaces.IRepositorioIncidentes;

import java.util.ArrayList;
import java.util.List;

@Data
public class RepositorioIncidentes implements IRepositorioIncidentes {
    private List<Incidente> incidentes;

    public RepositorioIncidentes() {
        this.incidentes = new ArrayList<>();
    }

    // ---------------------------------------------------------------------------------

    @Override
    public void agregarIncidente(Incidente incidente) {
        incidentes.add(incidente);
    }
    @Override
    public List<Incidente> obtenerTodasLosIncidentes() {
        return incidentes;
    }
    @Override
    public List<Incidente> buscarPorHeladera(Heladera heladera){
        return heladera.getIncidentes();
    }

    @Override
    public Incidente obtenerIncidentePorId(String id) {
        return null;
    }

}