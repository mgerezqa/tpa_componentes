package repositorios.reposEnMemoria;
import domain.heladera.Heladera.Heladera;
import domain.puntos.CategoriaOferta;
import domain.usuarios.Colaborador;
import domain.usuarios.Usuario;
import lombok.Data;
import repositorios.interfaces.IRepositorioColaboradores;
import repositorios.interfaces.IRepositorioUsuarios;

import java.util.Optional;

@Data
public class RepositorioColaboradores implements IRepositorioColaboradores {
    //TODO
    @Override
    public void alta(Colaborador colaborador) {

    }
    //TODO
    @Override
    public Colaborador buscarColaborador(Colaborador colaborador) {
        return null;
    }
    //TODO
    @Override
    public Colaborador buscarColaborador(Integer id) {
        return null;
    }
}
