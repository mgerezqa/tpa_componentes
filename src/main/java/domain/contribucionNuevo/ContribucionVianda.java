package domain.contribucionNuevo;

import domain.heladera.Heladera.Heladera;
import domain.usuariosNuevo.Colaborador;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class ContribucionVianda extends Contribucion{
    private LocalDate fechaVencimiento;
    private Heladera heladeraActual;

    // ============================================================ //

    // < CONSTRUCTOR > //
    public ContribucionVianda(LocalDate fechaVencimiento, LocalDate fechaDonacion, Colaborador colaboradorQueLaDono) {
        super(TipoContribucion.VIANDA);
        this.fechaVencimiento = fechaVencimiento;
        this.registrarFechaDeContribucion(fechaDonacion);
        this.registrarColaborador(colaboradorQueLaDono);
    }
    public ContribucionVianda(){
        super(TipoContribucion.VIANDA);
    }

    // ============================================================ //

    // < INGRESO DE VIANDAS A UNA HELADERA > //

    public void ingresarViandaAHeladera(Heladera heladera) throws Exception{
        if(heladera.getCapacidadMax() > heladera.getCapacidadActual()){
            heladeraActual = heladera;
            heladera.setCapacidadActual(heladera.getCapacidadActual()+1);
        }
        else {
            throw new Exception("La vianda no puede ser ingresada en la heladera seleccionada");
        }
    }



    // En este caso, la heladera actual de una vianda no es ingresada en el constructor (cuando se crea la instancia de la vianda),
    // sino que debe llamar al metodo "ingresarViandaAHeladera(heladera)", y ver si su capacidad es suficiente como para almacenarla.

}
