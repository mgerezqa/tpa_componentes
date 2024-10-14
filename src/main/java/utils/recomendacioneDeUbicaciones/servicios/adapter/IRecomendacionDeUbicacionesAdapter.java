package utils.recomendacioneDeUbicaciones.servicios.adapter;

import utils.recomendacioneDeUbicaciones.entidades.Comunidad;

import java.io.IOException;
import java.util.List;

public interface IRecomendacionDeUbicacionesAdapter {
    List<Comunidad> comunidadesRecomendadas(String token, Float latitud, Float longitud,  Integer max, Float distanciaMax) throws IOException;
}