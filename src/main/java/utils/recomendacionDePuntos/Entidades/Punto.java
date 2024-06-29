package utils.recomendacionDePuntos.Entidades;

import lombok.Getter;

public class Punto {
    @Getter
    private Double latitud;
    @Getter
    private Double longitud;

    public Punto(Double latitud, Double longitud) {
        this.latitud = latitud;
        this.longitud = longitud;
    }
}
