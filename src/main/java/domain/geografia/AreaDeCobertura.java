package domain.geografia;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class AreaDeCobertura {

    @Getter @Setter
    private List<Ciudad> ciudades;

    // ============================================================ //
    // < CONSTRUCTOR > //

    public AreaDeCobertura(List<Ciudad> ciudades) {
        this.ciudades = ciudades;
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

    // Inicialmente, lo pensé como que cada "ubicación" representa una zona, ejemplo:
    // Ubicaciones [Palermo, Belgrano, Tigre, Flores]

    // También se me ocurrió la idea de pensarlo como una "circunferencia"
    // Estableciendo un "centro" y un "radio".

}
