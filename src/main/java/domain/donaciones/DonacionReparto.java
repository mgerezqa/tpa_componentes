package domain.donaciones;

import domain.heladera.Heladera;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@ToString(callSuper = true)
@Getter
public class DonacionReparto extends Donacion {
    private Heladera heladeraOrigen;
    private Heladera heladeraDestino;
    private Integer cantidad;
    private Motivo motivo;
    private LocalDate fechaDeDonacion;

    public DonacionReparto(Heladera heladeraOrigen, Heladera heladeraDestino, Integer cantidad, Motivo motivo, LocalDate fechaDeDonacion) {
        super(TipoDonacion.DONA_REPARTO);
        this.heladeraOrigen = heladeraOrigen;
        this.heladeraDestino = heladeraDestino;
        this.cantidad = cantidad;
        this.motivo = motivo;
        this.fechaDeDonacion = fechaDeDonacion;
    }
    public DonacionReparto(){
        super(TipoDonacion.DONA_REPARTO);
    }
}
