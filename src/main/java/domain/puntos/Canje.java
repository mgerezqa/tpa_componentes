package domain.puntos;

import domain.usuarios.Colaborador;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "canjes")
public class Canje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "colaborador_id", nullable = false)
    private Colaborador canjeador;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "oferta_id", nullable = false)
    private Oferta ofertaCanjeada;

    @Column(name = "puntos")
    private Integer puntosGastados;
    @Column(name = "fecha_canje", nullable = false,columnDefinition = "DATETIME")
    private LocalDateTime fechaYHoraCanje;

    public Canje(Colaborador canjeador, Oferta ofertaCanjeada, Integer puntosGastados) {
        this.canjeador = canjeador;
        this.ofertaCanjeada = ofertaCanjeada;
        this.puntosGastados = puntosGastados;
        this.fechaYHoraCanje = LocalDateTime.now();
    }
}
