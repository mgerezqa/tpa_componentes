package domain.contribucionNuevo;

import domain.donaciones.Motivo;
import domain.heladera.Heladera.Heladera;
import domain.usuariosNuevo.ColaboradorFisico;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ContribucionDistribucion extends Contribucion{
    private Heladera heladeraOrigen;
    private Heladera heladeraDestino;
    private Integer cantidad;
    private Motivo motivo;
    private LocalDate fechaDeDonacion;


    public ContribucionDistribucion(Heladera heladeraOrigen, Heladera heladeraDestino, Integer cantidad, Motivo motivo, LocalDate fechaDeDonacion, ColaboradorFisico colaboradorQuelaDono){
        super(TipoContribucion.DISTRIBUCION);
        this.heladeraOrigen = heladeraOrigen;
        this.heladeraDestino = heladeraDestino;
        this.cantidad = cantidad;
        this.motivo = motivo;
        this.fechaDeDonacion = fechaDeDonacion;
        this.registrarColaborador(colaboradorQuelaDono);
    }
    public ContribucionDistribucion(){
        super(TipoContribucion.DISTRIBUCION);
    }
}
