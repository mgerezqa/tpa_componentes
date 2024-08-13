package domain.contribucionNuevo;

import domain.usuariosNuevo.Colaborador;
import lombok.Getter;

@Getter
public abstract class Contribucion {
    private Colaborador colaboradorResponsable;
    private TipoContribucion tipoContribucion;

    public Contribucion(TipoContribucion unTipo){
        this.tipoContribucion = unTipo;
    }
    public void registrarColaborador(Colaborador unColab){
        this.colaboradorResponsable = unColab;
    }
}
