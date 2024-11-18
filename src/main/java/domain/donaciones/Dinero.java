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
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "donaciones_dinero")
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

    @Override
    public String getTipo() {
        return "Dinero";
    }
}
