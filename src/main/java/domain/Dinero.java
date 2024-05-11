package domain;

import java.time.LocalDate;

public class Dinero implements Donable{
    private Integer cantidad;
    private FrecuenciaDeDonacion frecuencia;
    private LocalDate fechaDeDonacion;

    public Dinero(Integer cantidad, FrecuenciaDeDonacion frecuencia, LocalDate fechaDeDonacion) {
        this.cantidad = cantidad;
        this.frecuencia = frecuencia;
        this.fechaDeDonacion = fechaDeDonacion;
    }

    @Override
    public boolean puedeserDonada() {
        return true;
    }

    @Override
    public String msgError() {
        return "El dinero no puede ser donado";
    }

}
