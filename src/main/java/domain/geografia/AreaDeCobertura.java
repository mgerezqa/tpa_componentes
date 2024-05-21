package domain.geografia;

import java.util.List;

public class AreaDeCobertura {

    private List<Ubicacion> ubicaciones;

    // ============================================================ //
    // < CONSTRUCTOR > //

    public AreaDeCobertura(List<Ubicacion> ubicaciones) {
        this.ubicaciones = ubicaciones;
    }

    // ============================================================ //

    public void agregarUbicacion(Ubicacion ubicacion) {
        ubicaciones.add(ubicacion);
    }

    public void eliminarUbicacion(Ubicacion ubicacion) {
        ubicaciones.remove(ubicacion);
    }

    public boolean estaEnArea(Ubicacion ubicacion) {
        return ubicaciones.contains(ubicacion);
    }

    // Inicialmente, lo pensé como que cada "ubicación" representa una zona, ejemplo:
    // Ubicaciones [Palermo, Belgrano, Tigre, Flores]

    // También se me ocurrió la idea de pensarlo como una "circunferencia"
    // Estableciendo un "centro" y un "radio".

}
