package domain.puntos;

import domain.usuarios.Colaborador;
import domain.usuarios.ColaboradorJuridico;
import lombok.Getter;
import lombok.Setter;

public class Oferta {
    @Setter @Getter
    private String nombre;
    @Setter @Getter
    private String descripcion;
    @Setter @Getter
    private CategoriaOferta categoria;
    @Setter @Getter
    private ColaboradorJuridico ofertante;
    @Setter @Getter
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
            throw new FalloCanjePuntosInsuficientes("No alcanzan los puntos para hacer el canje.");
        }
    }
}
