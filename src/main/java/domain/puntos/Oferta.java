package domain.puntos;

import domain.usuarios.ColaboradorJuridico;
import lombok.Getter;
import lombok.Setter;

public class Oferta {
    @Setter @Getter public String nombre;
    @Setter @Getter public String descripcion;
    @Setter @Getter public CategoriaOferta categoria;
    @Setter @Getter public ColaboradorJuridico ofertante;
    @Setter @Getter public Integer costoPuntos;

    public Oferta(String nombre, String descripcion, CategoriaOferta categoria, ColaboradorJuridico ofertante, Integer costoPuntos) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.ofertante = ofertante;
        this.costoPuntos = costoPuntos;
    }
}
