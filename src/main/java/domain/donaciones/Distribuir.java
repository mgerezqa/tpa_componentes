package domain.donaciones;

import domain.heladera.Heladera.Heladera;
import domain.usuarios.ColaboradorFisico;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;



@Entity
@Table(name = "donaciones_distribuir")
public class Distribuir {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "heladera_origen_id", nullable = false)
    private Heladera heladeraOrigen;

    @ManyToOne
    @JoinColumn(name = "heladera_destino_id", nullable = false)
    private Heladera heladeraDestino;

    @Getter
    @Setter
    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    @Enumerated(EnumType.STRING)
    @Column(name = "motivo", nullable = false)
    private Motivo motivo;

    @Getter
    @Setter
    @Column(name = "fecha_donacion", nullable = false)
    private LocalDate fechaDeDonacion;

    @ManyToOne
    @JoinColumn(name = "colaborador_id", nullable = false)
    @Getter
    private ColaboradorFisico colaboradorQueLaDono;

    public Distribuir(Long id, Heladera heladeraOrigen, Heladera heladeraDestino, Integer cantidad, LocalDate fechaDeDonacion, Motivo motivo, ColaboradorFisico colaboradorQueLaDono) {
        this.id = id;
        this.heladeraOrigen = heladeraOrigen;
        this.heladeraDestino = heladeraDestino;
        this.cantidad = cantidad;
        this.fechaDeDonacion = fechaDeDonacion;
        this.motivo = motivo;
        this.colaboradorQueLaDono = colaboradorQueLaDono;
    }
}
