package domain.geografia;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Ubicacion {
    private Float latitud;
    private Float longitud;
    private Calle calle;
    private Provincia provincia;
    private Localidad localidad;
    private Barrio barrio;

    public Ubicacion(Float latitud, Float longitud, Calle calle) {
        this.latitud = latitud;
        this.longitud = longitud;
        this.calle = calle;
    }

    public Ubicacion(Provincia provincia, Localidad localidad, Barrio barrio) {
        this.provincia = provincia;
        this.localidad = localidad;
        this.barrio = barrio;
    }



}
