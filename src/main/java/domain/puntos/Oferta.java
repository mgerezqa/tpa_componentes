package domain.puntos;

import domain.excepciones.ExcepcionCanjePuntosInsuficientes;
import domain.usuarios.Colaborador;
import domain.usuarios.ColaboradorJuridico;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "Oferta")
@Data
@NoArgsConstructor
public class Oferta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "descripcion")
    private String descripcion;
    @Enumerated(EnumType.STRING)
    @Column(name = "categoria")
    private CategoriaOferta categoria;
    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST},fetch = FetchType.LAZY)
    @JoinColumn(name = "id_colaborador_juridico", referencedColumnName = "id")
    private ColaboradorJuridico ofertante;
    @Column(name = "costo")
    private Integer costoPuntos;

    public Oferta(String nombre, String descripcion, CategoriaOferta categoria, ColaboradorJuridico ofertante, Integer costoPuntos) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.ofertante = ofertante;
        this.costoPuntos = costoPuntos;
    }

    public void hacerCanje(Colaborador colaborador, Oferta oferta){
        if (oferta.getCostoPuntos() <= colaborador.getPuntosAcumulados()) {
            // Canje Exitoso
            colaborador.restarPuntos(oferta.getCostoPuntos());
        }else {
            throw new ExcepcionCanjePuntosInsuficientes("No alcanzan los puntos para hacer el canje.");
        }
    }
}
