package repositorios;
import domain.heladera.Heladera.Heladera;
import domain.incidentes.Incidente;
import lombok.Data;
import repositorios.IRepositorioIncidentes;

import java.util.ArrayList;
import java.util.List;

@Data
public class RepositorioIncidentes implements IRepositorioIncidentes {

    private List<Incidente> incidentes;

    public RepositorioIncidentes() {
        this.incidentes = new ArrayList<>();
    }

    // ---------------------------------------------------------------------------------

    public void agregarIncidente(Incidente incidente) {
        incidentes.add(incidente);
    }
    public List<Incidente> obtenerTodasLosIncidentes() {
        return incidentes;
    }
    public List<Incidente> buscarPorHeladera(Heladera heladera){
        return heladera.getIncidentes();
    }

}
