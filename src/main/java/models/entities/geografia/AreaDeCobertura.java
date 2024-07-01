package models.entities.geografia;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AreaDeCobertura {

    @Getter
    private String nombreAreaDeCobertura;

    @Getter @Setter
    private List<Ciudad> ciudades;

    // ============================================================ //
    // < CONSTRUCTOR > //

    public AreaDeCobertura(String nombreAreaDeCobertura, List<Ciudad> ciudades) {
        this.nombreAreaDeCobertura = nombreAreaDeCobertura;
        this.ciudades = (ciudades != null) ? ciudades : new ArrayList<>();
    }

    // ============================================================ //

    public void agregarUbicacion(Ciudad ciudad) {
        ciudades.add(ciudad);
    }

    public void eliminarUbicacion(Ciudad ciudad) {
        ciudades.remove(ciudad);
    }

    public boolean estaEnArea(Ciudad ciudad) {
        return ciudades.contains(ciudad);
    }

    public int cantidadDeCiudades(){
        return ciudades.size();
    }

    // Inicialmente, lo pensé como que cada "ubicación" representa una zona, ejemplo:
    // Ubicaciones [Palermo, Belgrano, Tigre, Flores]

    // También se me ocurrió la idea de pensarlo como una "circunferencia"
    // Estableciendo un "centro" y un "radio".

}
