package utils.recomendacioneDeUbicaciones.servicios.adapter;

import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.Query;
import utils.recomendacioneDeUbicaciones.entidades.Comunidad;
import utils.recomendacioneDeUbicaciones.entidades.ListadoDeComunidades;

import java.io.IOException;
import java.util.List;

public interface IRecomendacionDeUbicacionesAdapter {
    List<Comunidad> comunidadesRecomendadas(String token, Float latitud, Float longitud,  Integer max, Float distanciaMax) throws IOException;

}
