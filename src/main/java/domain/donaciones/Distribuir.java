package domain.donaciones;

import domain.heladera.Heladera;
import domain.usuarios.Colaborador;
import domain.usuarios.ColaboradorFisico;
import lombok.Getter;

import java.time.LocalDate;

public class Distribuir  {
    private Heladera heladeraOrigen;
    private Heladera heladeraDestino;
    private Integer cantidad;
    private Motivo motivo;
    private LocalDate fechaDeDonacion;
    @Getter ColaboradorFisico colaboradorQueLaDono;



    public Distribuir(Heladera heladeraOrigen, Heladera heladeraDestino, Integer cantidad, Motivo motivo, LocalDate fechaDeDonacion, ColaboradorFisico colaboradorQuelaDono){
        this.heladeraOrigen = heladeraOrigen;
        this.heladeraDestino = heladeraDestino;
        this.cantidad = cantidad;
        this.motivo = motivo;
        this.fechaDeDonacion = fechaDeDonacion;
        this.colaboradorQueLaDono = colaboradorQuelaDono;


    }


}
