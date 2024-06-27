package repositorios;
import domain.heladera.Heladera.Heladera;
import java.util.ArrayList;
import java.util.List;

public class RepositorioHeladeras {
    private List<Heladera> heladeras;

    public RepositorioHeladeras() {
        this.heladeras = new ArrayList<>();
    }

    public void darDeAlta(Heladera heladera) {
        heladeras.add(heladera);
    }
    public void darDeBaja(Heladera heladera) {
        heladeras.remove(heladera);
    }
    public List<Heladera> obtenerTodasLasHeladeras() {
        return heladeras;
    }
}
