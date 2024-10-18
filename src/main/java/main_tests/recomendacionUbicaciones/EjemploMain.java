package main_tests.recomendacionUbicaciones;

import utils.recomendacioneDeUbicaciones.entidades.Comunidad;
import utils.recomendacioneDeUbicaciones.entidades.ListadoDeComunidades;
import utils.recomendacioneDeUbicaciones.servicios.RecomedacionDeUbicaciones;

import java.io.IOException;

public class EjemploMain {

    public static void main(String[] args) throws IOException {
        RecomedacionDeUbicaciones servicioRecomendacionUbicaciones = RecomedacionDeUbicaciones.getInstance();

        Float latitud = -34.f;
        Float longitud = -58.f;
        Integer max = 3;
        Float distanciaMax = 100f;

        ListadoDeComunidades listadoDeComunidades = servicioRecomendacionUbicaciones.listadoCumunidades("Uo0lhGZ_0baxg_miCp-rh6944wpHLJGJ",latitud, longitud,max,distanciaMax);
        System.out.println(listadoDeComunidades);
        for(Comunidad comunidadRecomendada : listadoDeComunidades.getComunidades()) {
            System.out.println("Id: "+ comunidadRecomendada.getId()+ ", Comunidad: "+comunidadRecomendada.getNombre() +", Latitud: " + comunidadRecomendada.getLat() + ", Longitud: " + comunidadRecomendada.getLon());
        }
    }
}
