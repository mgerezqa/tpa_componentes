package domain.donaciones;
import domain.usuarios.ColaboradorFisico;
import domain.heladera.Heladera.Heladera;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "donaciones_viandas")
public class Vianda extends Donacion {
    @Column(name = "fecha_vencimiento", columnDefinition = "DATE")
    private LocalDate fechaVencimiento;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "heladera_id")
    private Heladera heladeraActual;

    @Column(name = "calorias")
    private Long calorias;
    @Column(name = "peso")
    private Long peso;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "cantidad")
    private Integer cantidad;
    // ============================================================ //

    // < CONSTRUCTOR > //
    public Vianda(LocalDate fechaVencimiento, LocalDate fechaDonacion, ColaboradorFisico colaboradorQueLaDono) {
        super(fechaDonacion, colaboradorQueLaDono);
        this.fechaVencimiento = fechaVencimiento;
    }
    public Vianda(String descripcion,LocalDate fechaVencimiento, Long calorias,Long peso,ColaboradorFisico colaboradorQueLaDono) {
        super(colaboradorQueLaDono);
        this.fechaVencimiento = fechaVencimiento;
        this.descripcion = descripcion;
        this.calorias = calorias;
        this.peso = peso;
    }

    private Boolean activa;

    // ============================================================ //

    // < INGRESO DE VIANDAS A UNA HELADERA > //

    public void ingresarViandaAHeladera(Heladera heladera) throws Exception{
        if (heladera.getCapacidadActual() == null) {
            heladera.setCapacidadActual(0);
        }
        if(heladera.getCapacidadMax() > heladera.getCapacidadActual()){
            heladeraActual = heladera;
            heladera.setCapacidadActual(heladera.getCapacidadActual()+1);
        }
        else {
            throw new Exception("La vianda no puede ser ingresada en la heladera seleccionada");
        }
    }
    @Override
    public String getTipo() {
        return "Vianda";
    }


    // En este caso, la heladera actual de una vianda no es ingresada en el constructor (cuando se crea la instancia de la vianda),
    // sino que debe llamar al metodo "ingresarViandaAHeladera(heladera)", y ver si su capacidad es suficiente como para almacenarla.

}
