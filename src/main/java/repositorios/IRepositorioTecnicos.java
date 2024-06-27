package repositorios;

import domain.tecnicos.Tecnico;

import java.util.List;

public interface IRepositorioTecnicos {
    void agregar(Tecnico tecnico);
    void eliminar(Tecnico tecnico);
    void buscar(Tecnico tecnico);
    List<Tecnico> obtenerTodosLosTecnicos();
    List<Tecnico> obtenerTecnicosActivos();
}
