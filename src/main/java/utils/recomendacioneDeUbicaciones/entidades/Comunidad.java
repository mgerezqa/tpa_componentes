package utils.recomendacioneDeUbicaciones.entidades;

import lombok.Data;

@Data
public class Comunidad {
    private Long id;
    private String nombre;
    private Float lat;
    private Float lon;

    public Comunidad(Long id, String nombre, Float lat, Float lon) {
        this.id = id;
        this.nombre = nombre;
        this.lat = lat;
        this.lon = lon;
    }
}
