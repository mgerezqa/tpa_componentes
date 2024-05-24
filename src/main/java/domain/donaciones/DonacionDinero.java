package domain.donaciones;

import java.time.LocalDate;

public class DonacionDinero extends Donacion {
    private Integer monto;
    private FrecuenciaDeDonacion frecuencia;
    private LocalDate fechaDeDonacion;

    public DonacionDinero(Integer unMonto, FrecuenciaDeDonacion frecuencia, LocalDate fechaDeDonacion) {
        super(TipoDonacion.DONA_DINERO);
        this.monto = unMonto;
        this.frecuencia = frecuencia;
        this.fechaDeDonacion = fechaDeDonacion;
    }


}
