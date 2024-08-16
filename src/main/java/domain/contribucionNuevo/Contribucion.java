package domain.contribucionNuevo;

import domain.usuariosNuevo.Colaborador;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public abstract class Contribucion {
    private Colaborador colaboradorResponsable;
    private TipoContribucion tipoContribucion;
    private LocalDate fechaDeContribucion;

    public Contribucion(TipoContribucion unTipo){
        this.tipoContribucion = unTipo;
    }
    public void registrarColaborador(Colaborador unColab){
        this.colaboradorResponsable = unColab;
    }
    public void registrarFechaDeContribucion(LocalDate unaFecha){
        this.fechaDeContribucion = unaFecha;
    }
}
