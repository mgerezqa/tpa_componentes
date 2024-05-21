package domain.donaciones;
import domain.usuarios.ColaboradorFisico;
import domain.heladera.Heladera;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Vianda  {

//    private LocalDateTime fechaVencimiento;
//    private LocalDateTime fechaDonacion;
    private LocalDate fechaVencimiento;
    private LocalDate fechaDonacion;

    @Getter private ColaboradorFisico colaboradorQueLaDono;

    @Setter @Getter
    private Heladera heladeraActual;

    // ============================================================ //

    // < CONSTRUCTOR > //
    public Vianda(LocalDate fechaVencimiento, LocalDate fechaDonacion, ColaboradorFisico colaboradorQueLaDono) {
        this.fechaVencimiento = fechaVencimiento;
        this.fechaDonacion = fechaDonacion;
        this.colaboradorQueLaDono = colaboradorQueLaDono;
    }

    // ============================================================ //

    // < INGRESO DE VIANDAS A UNA HELADERA > //

    public void ingresarViandaAHeladera(Heladera heladera) throws Exception{
        if(heladera.getCapacidadMax() < heladera.getCapacidadActual()){
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
