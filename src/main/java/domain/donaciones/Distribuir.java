package domain.donaciones;

import domain.heladera.Heladera;

import java.time.LocalDate;

public class Distribuir implements Donable {
    private Heladera heladeraOrigen;
    private Heladera heladeraDestino;
    private Integer cantidad;
    private Motivo motivo;
    private LocalDate fechaDeDonacion;


    @Override
    public boolean puedeserDonada() {
        return true;
    }

    @Override
    public String msgError() {
        return "La distribucion no puede ser donada";
    }

    public Distribuir(Heladera heladeraOrigen,Heladera heladeraDestino, Integer cantidad, Motivo motivo,LocalDate fechaDeDonacion){
        this.heladeraOrigen = heladeraOrigen;
        this.heladeraDestino = heladeraDestino;
        this.cantidad = cantidad;
        this.motivo = motivo;
        this.fechaDeDonacion = fechaDeDonacion;
    }


}
