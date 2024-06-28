package repositorios.interfaces;

import domain.usuarios.Usuario;
import lombok.Data;


public interface IRepositorioUsuarios {
    void agregarUsuario(Usuario usuario);
    Usuario buscarUsuario(Usuario usuario);
    Usuario buscarUsuarioPorNombre(String nombreUsuario);
}
