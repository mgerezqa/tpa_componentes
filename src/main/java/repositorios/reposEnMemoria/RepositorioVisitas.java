package repositorios.reposEnMemoria;
import domain.visitas.Visita;
import repositorios.interfaces.IRepositorioVisitas;

import java.util.List;

public class RepositorioVisitas implements IRepositorioVisitas {

    List<Visita> visitas;

    public RepositorioVisitas(List<Visita> visitas) {
        this.visitas = visitas;
    }

    @Override
    public void agregarVisita(Visita visita) {
        visitas.add(visita);
    }

    @Override
    public Visita buscarVisita(Visita visita) {
        return null;
    }
}
