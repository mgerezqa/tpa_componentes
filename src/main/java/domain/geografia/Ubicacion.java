package domain.geografia;

import domain.geografia.Calle;

public class Ubicacion {
    private Float latitud;
    private Float longitud;
    private Calle calle;

    public Ubicacion(Float latitud, Float longitud, Calle calle) {
        this.latitud = latitud;
        this.longitud = longitud;
        this.calle = calle;
    }
}
