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
public class Vianda  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha_vencimiento", columnDefinition = "DATE")
    private LocalDate fechaVencimiento;

    @Column(name = "fecha_donacion", columnDefinition = "DATE")
    private LocalDate fechaDonacion;

    @ManyToOne
    @JoinColumn(name = "colaborador_id")
    private ColaboradorFisico colaboradorQueLaDono;

    @ManyToOne
    @JoinColumn(name = "heladera_id")
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
