package repositorios.interfaces;
import domain.usuarios.Tecnico;

import java.util.List;

public interface IRepositorioTecnicos {
    void darDeAlta(Tecnico tecnico);
    void darDeBaja(Tecnico tecnico);
    List<Tecnico> obtenerTodosLosTecnicos();
    List<Tecnico> obtenerTecnicosActivos();
    Tecnico obtenerTecnicoPorNumeroDocumento(String numeroDocumento);
}
