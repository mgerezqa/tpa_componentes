package domain.donaciones;

import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@ToString(callSuper = true)
@Getter
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


    public DonacionDinero() {
        super(TipoDonacion.DONA_DINERO);
    }
}
