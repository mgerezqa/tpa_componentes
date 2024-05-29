package domain.donaciones;

import domain.heladera.Heladera.Heladera;
import domain.usuarios.ColaboradorFisico;
import lombok.Getter;

import java.time.LocalDate;

public class Distribuir extends Contribucion {
    private Heladera heladeraOrigen;
    private Heladera heladeraDestino;
    @Getter private Integer cantidad;
    private Motivo motivo;
    private LocalDate fechaDeDonacion;

    public Distribuir() {
        super(TipoContribucion.HELADERA);
    }

    public Distribuir(Heladera heladeraOrigen, Heladera heladeraDestino, Integer cantidad, Motivo motivo, LocalDate fechaDeDonacion){
        super(TipoContribucion.DISTRIBUCION);
        this.heladeraOrigen = heladeraOrigen;
        this.heladeraDestino = heladeraDestino;
        this.cantidad = cantidad;
        this.motivo = motivo;
        this.fechaDeDonacion = fechaDeDonacion;


    }


}
