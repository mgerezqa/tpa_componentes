package domain.donaciones;

import domain.usuarios.Colaborador;
import lombok.Getter;

import java.time.LocalDate;

public class Dinero extends Contribucion {
    @Getter private Integer cantidad;
    private FrecuenciaDeDonacion frecuencia;
    private LocalDate fechaDeDonacion;

    public Dinero() {
        super(TipoContribucion.DINERO);
    }

    public Dinero(Integer cantidad, FrecuenciaDeDonacion frecuencia, LocalDate fechaDeDonacion) {
        super(TipoContribucion.DINERO);
        this.cantidad = cantidad;
        this.frecuencia = frecuencia;
        this.fechaDeDonacion = fechaDeDonacion;

    }




}
