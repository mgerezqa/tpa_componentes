package domain.donaciones;

import domain.usuarios.Colaborador;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "donaciones_dinero")
public class Dinero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    @Enumerated(EnumType.STRING)
    private FrecuenciaDeDonacion frecuencia;


    @Column(name = "fecha_donacion", nullable = false)
    private LocalDate fechaDeDonacion;

    @ManyToOne
    @JoinColumn(name = "colaborador_id", nullable = false)
    private Colaborador colaboradorQueLaDono;

    public Dinero(Integer cantidad, FrecuenciaDeDonacion frecuencia, LocalDate fechaDeDonacion, Colaborador colaboradorQueLaDono) {
        this.cantidad = cantidad;
        this.frecuencia = frecuencia;
        this.fechaDeDonacion = fechaDeDonacion;
        this.colaboradorQueLaDono = colaboradorQueLaDono;
    }
}
