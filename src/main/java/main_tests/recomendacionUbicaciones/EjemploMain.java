package main_tests.recomendacionUbicaciones;
import utils.recomendacionDePuntos.Entidades.ListadoDePuntos;
import utils.recomendacionDePuntos.Entidades.Punto;
import utils.recomendacionDePuntos.Servicios.ServicioRecomendacionDePuntos;
import utils.recomendacioneDeUbicaciones.entidades.ApiKey;
import utils.recomendacioneDeUbicaciones.entidades.Comunidad;
import utils.recomendacioneDeUbicaciones.entidades.ListadoDeComunidades;
import utils.recomendacioneDeUbicaciones.servicios.RecomedacionDeUbicaciones;

import java.io.IOException;

public class EjemploMain {

    public static void main(String[] args) throws IOException {
        RecomedacionDeUbicaciones servicioRecomendacionUbicaciones = RecomedacionDeUbicaciones.getInstance();

        Float latitud = -35.9987f;
        Float longitud = -37.7899f;
        Integer max = 2;
        Float distanciaMax = 100f;

        ListadoDeComunidades listadoDeComunidades = servicioRecomendacionUbicaciones.listadoCumunidades("Uo0lhGZ_0baxg_miCp-rh6944wpHLJGJ",latitud, longitud,max,distanciaMax);

        for(Comunidad comunidadRecomendada : listadoDeComunidades.getComunidades()) {
            System.out.println("Latitud: " + comunidadRecomendada.getLat() + ", Longitud: " + comunidadRecomendada.getLon());
        }
    }
}
