package domain.geografia.area;
import domain.geografia.Barrio;
import domain.geografia.Localidad;
import domain.geografia.Provincia;
import domain.geografia.Ubicacion;
import domain.heladera.Heladera.Heladera;
import domain.usuarios.ColaboradorFisico;
import utils.calculadorDistancia.ICalculadorDistanciaKM;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

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


    private Provincia provincia;
    private Set<Localidad> localidades = new HashSet<>();
    private Set<Barrio> barrios = new HashSet<>();

    public AreaDeCobertura(Ubicacion ubicacionPrincipal, TamanioArea tamanioArea) {
        this.ubicacionPrincipal = ubicacionPrincipal;
        this.tamanioArea = tamanioArea;
    }

    public Boolean estaEnElArea(Ubicacion ubicacion) {
        return (iCalculadorDistanciaKM.calcularDistanciaKM(ubicacionPrincipal, ubicacion)
                <= tamanioArea.getLongitud());
    }

    // ============================================================ //
    // < Agregado para controlar el area de suscripciones > //
    // ============================================================ //

    public AreaDeCobertura() {

    }

        public void agregarLocalidad(Localidad localidad) {
        localidades.add(localidad);
    }

    public void agregarBarrio(Barrio barrio) {
        barrios.add(barrio);
    }


    public Boolean estaEnZonaQueFrecuenta(ColaboradorFisico colaborador, Heladera heladera) {
        //el colaborador está en la misma provincia que la ubicacion de la heladera
        //y además se cumple que alguna de las localidades del colaborador coincide con la localidad de la heladera
        //y además alguno de los barrios donde el colaborador frecuenta coincide con el barrio de la heladera
        return estaEnLaMismaProvincia(colaborador, heladera) && estaEnLaMismaLocalidad(colaborador, heladera) && estanEnElMismoBarrio(colaborador, heladera);

    }

    public Boolean estaEnLaMismaProvincia(ColaboradorFisico colaborador, Heladera heladera) {
        if (colaborador.getZonaQueFrecuenta().getProvincia().equals(heladera.getUbicacion().getProvincia())) {
            return true;
        }
        return false;
    }

    public Boolean estaEnLaMismaLocalidad(ColaboradorFisico colaborador, Heladera heladera) {
        if (colaborador.getZonaQueFrecuenta().getLocalidades().contains(heladera.getUbicacion().getLocalidad())) {
            return true;
        }
        return false;
    }

    public Boolean estanEnElMismoBarrio(ColaboradorFisico colaborador, Heladera heladera){
        if (colaborador.getZonaQueFrecuenta().getBarrios().contains(heladera.getUbicacion().getBarrio())) {
            return true;
        }
        return false;
    }



}
