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
public class Distribuir {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @Column(name = "fecha_donacion", nullable = false)
    private LocalDate fechaDeDonacion;

    @ManyToOne
    @JoinColumn(name = "colaborador_id", nullable = false)
    private ColaboradorFisico colaboradorQueLaDono;

    public Distribuir(Heladera heladeraOrigen, Heladera heladeraDestino, Integer cantidad, LocalDate fechaDeDonacion, ColaboradorFisico colaboradorQueLaDono) {
        this.heladeraOrigen = heladeraOrigen;
        this.heladeraDestino = heladeraDestino;
        this.cantidad = cantidad;
        this.fechaDeDonacion = fechaDeDonacion;
        this.colaboradorQueLaDono = colaboradorQueLaDono;
    }
}
