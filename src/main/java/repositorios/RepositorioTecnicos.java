package repositorios;

import domain.tecnicos.Tecnico;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RepositorioTecnicos implements IRepositorioTecnicos{
    private List<Tecnico> tecnicos;

    public RepositorioTecnicos() {
        this.tecnicos = new ArrayList<>();
    }

    @Override
    public void agregar(Tecnico tecnico) {
        tecnicos.add(tecnico);
    }
    @Override
    public void eliminar(Tecnico tecnico) {
        tecnicos.remove(tecnico);
    }
    @Override
    public void buscar(Tecnico tecnico){}
    @Override
    public List<Tecnico> obtenerTodosLosTecnicos() {
        return tecnicos;
    }
    @Override
    public List<Tecnico> obtenerTecnicosActivos() {
        return tecnicos.stream().filter(Tecnico::getActivo).collect(Collectors.toList());
    }
}
