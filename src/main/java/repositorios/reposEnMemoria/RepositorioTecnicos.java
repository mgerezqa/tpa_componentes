package repositorios.reposEnMemoria;

import domain.usuarios.Tecnico;
import repositorios.interfaces.IRepositorioTecnicos;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RepositorioTecnicos implements IRepositorioTecnicos {
    private List<Tecnico> tecnicos;

    public RepositorioTecnicos() {
        this.tecnicos = new ArrayList<>();
    }

    @Override
    public void darDeAlta(Tecnico tecnico) {
        tecnicos.add(tecnico);
    }
    @Override
    public void darDeBaja(Tecnico tecnico) {
        tecnicos.remove(tecnico);
    }
    @Override
    public List<Tecnico> obtenerTodosLosTecnicos() {
        return tecnicos;
    }
    @Override
    public List<Tecnico> obtenerTecnicosActivos() {
        return tecnicos.stream().filter(Tecnico::getActivo).collect(Collectors.toList());
    }

    // TODO
    @Override
    public Tecnico obtenerTecnicoPorNumeroDocumento(String numeroDocumento) {
        return null;
    }
}
