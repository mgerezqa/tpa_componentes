package repositorios.reposEnMemoria;
import domain.heladera.Heladera.Heladera;
import domain.incidentes.Incidente;
import repositorios.interfaces.IRepositorioHeladeras;

import java.util.ArrayList;
import java.util.List;

public class RepositorioHeladeras implements IRepositorioHeladeras {
    private List<Heladera> heladeras;

    public RepositorioHeladeras() {
        this.heladeras = new ArrayList<>();
    }

    @Override
    public void darDeAlta(Heladera heladera) {
        heladeras.add(heladera);
    }
    @Override
    public void darDeBaja(Heladera heladera) {
        heladeras.remove(heladera);
    }
    @Override
    public List<Heladera> obtenerTodasLasHeladeras() {
        return heladeras;
    }

    // TODO
    @Override
    public Heladera obtenerHeladeraPorNombre(String nombreIdentificador) {
        return null;
    }

}
