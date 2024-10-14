package utils.recomendacionDePuntos.Servicios.Adapter;

import utils.recomendacionDePuntos.Entidades.ListadoDePuntos;
import utils.recomendacionDePuntos.Entidades.Punto;
import utils.recomendacionDePuntos.Servicios.ServicioRecomendacionDePuntos;

import java.io.IOException;
import java.util.List;

public class RecomendacionDePuntosAdapter implements RecomendacionDePuntosAdapterInterface {
    private ServicioRecomendacionDePuntos servicioRecomendacionDePuntos;

    public RecomendacionDePuntosAdapter(){
        this.servicioRecomendacionDePuntos = ServicioRecomendacionDePuntos.getInstance();
    }
    @Override
    public List<Punto> obtenerPuntosRecomendados(Double latitud, Double longitud, Double radio) throws IOException{
        ListadoDePuntos listadoDePuntos = servicioRecomendacionDePuntos.listadoDePuntos(latitud, longitud, radio);
        return listadoDePuntos.getPuntosRecomendados();
    }
}
