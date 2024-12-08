package domain.donaciones;

import domain.usuarios.Colaborador;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "donaciones_dinero")
@Builder
public class Dinero extends Donacion{

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    @Enumerated(EnumType.STRING)
    private FrecuenciaDeDonacion frecuencia;

    public Dinero(Integer cantidad, FrecuenciaDeDonacion frecuencia, LocalDate fechaDeDonacion, Colaborador colaboradorQueLaDono) {
        super(fechaDeDonacion,colaboradorQueLaDono);
        this.cantidad = cantidad;
        this.frecuencia = frecuencia;
    }
    public Dinero(Integer cantidad, FrecuenciaDeDonacion frecuencia, Colaborador colaboradorQueLaDono) {
        super(colaboradorQueLaDono);
        this.cantidad = cantidad;
        this.frecuencia = frecuencia;
    }

    @Override
    public String getTipo() {
        return "Dinero";
    }
}
