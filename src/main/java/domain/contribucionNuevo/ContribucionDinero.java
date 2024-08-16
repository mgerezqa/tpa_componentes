package domain.contribucionNuevo;

import domain.donaciones.FrecuenciaDeDonacion;
import domain.usuariosNuevo.Colaborador;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter @Setter
public class ContribucionDinero extends Contribucion {
    private Integer cantidad;
    private FrecuenciaDeDonacion frecuencia;

    public ContribucionDinero(Integer cantidad, FrecuenciaDeDonacion frecuencia, LocalDate fechaDeDonacion, Colaborador colaboradorQueLaDono) {
        super(TipoContribucion.DINERO);
        this.cantidad = cantidad;
        this.frecuencia = frecuencia;
        this.registrarFechaDeContribucion(fechaDeDonacion);
        this.registrarColaborador(colaboradorQueLaDono);
    }
    public ContribucionDinero(){
        super(TipoContribucion.DINERO);
    }
}
