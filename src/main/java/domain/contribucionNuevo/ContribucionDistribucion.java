package domain.contribucionNuevo;

import domain.donaciones.Motivo;
import domain.heladera.Heladera.Heladera;
import domain.usuariosNuevo.ColaboradorFisico;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class ContribucionDistribucion extends Contribucion{
    private Heladera heladeraOrigen;
    private Heladera heladeraDestino;
    private Integer cantidad;
    private Motivo motivo;

    public ContribucionDistribucion(Heladera heladeraOrigen, Heladera heladeraDestino, Integer cantidad, Motivo motivo, LocalDate fechaDeDonacion, ColaboradorFisico colaboradorQuelaDono){
        super(TipoContribucion.DISTRIBUCION);
        this.heladeraOrigen = heladeraOrigen;
        this.heladeraDestino = heladeraDestino;
        this.cantidad = cantidad;
        this.motivo = motivo;
        this.registrarFechaDeContribucion(fechaDeDonacion);
        this.registrarColaborador(colaboradorQuelaDono);
    }
    public ContribucionDistribucion(){
        super(TipoContribucion.DISTRIBUCION);
    }
}
