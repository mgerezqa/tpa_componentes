package domain.puntos;

import domain.donaciones.Donacion;
import domain.excepciones.ExcepcionCanjePuntosInsuficientes;
import domain.usuarios.Colaborador;
import domain.usuarios.ColaboradorJuridico;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "ofertas")
@Getter
@Setter
@NoArgsConstructor
public class Oferta extends Donacion {
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "descripcion")
    private String descripcion;
    @Enumerated(EnumType.STRING)
    @Column(name = "categoria")
    private CategoriaOferta categoria;
    @Column(name = "costo")
    private Integer costoPuntos;
    @Column(name = "foto")
    private String foto;

    public Oferta(String nombre, String descripcion, CategoriaOferta categoria, ColaboradorJuridico ofertante, Integer costoPuntos) {
        super(LocalDate.now(), ofertante);
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.categoria = categoria;
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

    @Override
    public String getTipo() {
        return "Oferta";
    }
}
