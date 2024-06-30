package repositorios.interfaces;

import domain.usuarios.Colaborador;
import domain.usuarios.Usuario;


public interface IRepositorioColaboradores {
    void alta(Colaborador colaborador);
    Colaborador buscarColaborador(Colaborador colaborador);
}
