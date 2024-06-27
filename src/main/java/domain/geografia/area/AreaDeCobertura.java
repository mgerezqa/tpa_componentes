package domain.geografia.area;
import domain.geografia.Ubicacion;
import domain.geografia.calculadorDistancia.ICalculadorDistanciaKM;
import lombok.Data;

//@Getter
//    private String nombreAreaDeCobertura;
//    @Getter @Setter
//    private List<Ciudad> ciudades;
//
//    // ============================================================ //
//    // < CONSTRUCTOR > //
//    // ============================================================ //
//    public AreaDeCobertura(String nombreAreaDeCobertura, List<Ciudad> ciudades) {
//        this.nombreAreaDeCobertura = nombreAreaDeCobertura;
//        this.ciudades = ciudades;
//    }
//
//    // ============================================================ //
//
//    public void agregarUbicacion(Ciudad ciudad) {
//        ciudades.add(ciudad);
//    }
//    public void eliminarUbicacion(Ciudad ciudad) {
//        ciudades.remove(ciudad);
//    }
//    public boolean estaEnArea(Ciudad ciudad) {
//        return ciudades.contains(ciudad);
//    }
//
//    // Inicialmente, lo pensé como que cada "ubicación" representa una zona, ejemplo:
//    // Ubicaciones [Palermo, Belgrano, Tigre, Flores]
//
//    // También se me ocurrió la idea de pensarlo como una "circunferencia"
//    // Estableciendo un "centro" y un "radio".

@Data
public class AreaDeCobertura {

    private Ubicacion ubicacionPrincipal;
    private TamanioArea tamanioArea;

    // Inyección de dependencias
    private ICalculadorDistanciaKM iCalculadorDistanciaKM;

    public AreaDeCobertura(Ubicacion ubicacionPrincipal, TamanioArea tamanioArea) {
        this.ubicacionPrincipal = ubicacionPrincipal;
        this.tamanioArea = tamanioArea;
    }

    public Boolean estaEnElArea(Ubicacion ubicacion){
        return (iCalculadorDistanciaKM.calcularDistanciaKM(ubicacionPrincipal, ubicacion)
                <= tamanioArea.getLongitud());
    }

}
