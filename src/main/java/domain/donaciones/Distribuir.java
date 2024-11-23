package domain.donaciones;

import domain.heladera.Heladera.Heladera;
import domain.usuarios.ColaboradorFisico;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;



@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "donaciones_distribuir")
public class Distribuir extends Donacion{

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "heladera_origen_id", nullable = false)
    private Heladera heladeraOrigen;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "heladera_destino_id", nullable = false)
    private Heladera heladeraDestino;

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    @Enumerated(EnumType.STRING)
    @Column(name = "motivo")
    private Motivo motivo;

    public Distribuir(Heladera heladeraOrigen, Heladera heladeraDestino, Integer cantidad, LocalDate fechaDeDonacion, ColaboradorFisico colaboradorQueLaDono) {
        super(fechaDeDonacion, colaboradorQueLaDono);
        this.heladeraOrigen = heladeraOrigen;
        this.heladeraDestino = heladeraDestino;
        this.cantidad = cantidad;
    }
    public Distribuir(Heladera heladeraOrigen, Heladera heladeraDestino,Integer cantidad,ColaboradorFisico colaboradorQueLaDono){
        super(colaboradorQueLaDono);
        this.heladeraOrigen = heladeraOrigen;
        this.heladeraDestino = heladeraDestino;
        this.cantidad = cantidad;
    }
    @Override
    public String getTipo() {
        return "Distribuir";
    }


}
