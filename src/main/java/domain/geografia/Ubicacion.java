package domain.geografia;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Embeddable
@NoArgsConstructor
public class Ubicacion {
    @Column(name = "latitud", nullable = false)
    private Float latitud;

    @Column(name = "longitud", nullable = false)
    private Float longitud;
    @Embedded
    private Calle calle;
    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST},fetch = FetchType.LAZY)
    @JoinColumn(name = "id_provincia", referencedColumnName = "id")
    private Provincia provincia;
    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST},fetch = FetchType.LAZY)
    @JoinColumn(name = "id_localidad", referencedColumnName = "id")
    private Localidad localidad;
    @Embedded
    private Barrio barrio;

    public Ubicacion(Float latitud, Float longitud, Calle calle) {
        this.latitud = latitud;
        this.longitud = longitud;
        this.calle = calle;
    }

    public double calcularDistanciaKmA(Ubicacion ubicacion1) {
        double latitud1 = ubicacion1.getLatitud();
        double longitud1 = ubicacion1.getLongitud();
        double latitud2 = this.getLatitud();
        double longitud2 = this.getLongitud();

        // Radio de la Tierra en kilómetros
        final double radioTierra = 6371.0;

        // Convertir latitud y longitud a radianes
        double lat1Rad = Math.toRadians(latitud1);
        double lon1Rad = Math.toRadians(longitud1);
        double lat2Rad = Math.toRadians(latitud2);
        double lon2Rad = Math.toRadians(longitud2);

        // Diferencias de latitud y longitud
        double deltaLat = lat2Rad - lat1Rad;
        double deltaLon = lon2Rad - lon1Rad;

        // Fórmula de Haversine
        double a = Math.pow(Math.sin(deltaLat / 2), 2) +
                Math.cos(lat1Rad) * Math.cos(lat2Rad) *
                        Math.pow(Math.sin(deltaLon / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // Distancia en kilómetros
        double distancia = radioTierra * c;

        return distancia;
    }

    public Ubicacion(Provincia provincia, Localidad localidad, Barrio barrio) {
        this.provincia = provincia;
        this.localidad = localidad;
        this.barrio = barrio;
    }



}
