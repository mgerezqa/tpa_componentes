package utils.recomendacioneDeUbicaciones.servicios;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;
import utils.recomendacioneDeUbicaciones.entidades.ApiKey;
import utils.recomendacioneDeUbicaciones.entidades.ListadoDeComunidades;

public interface IRecomendacionDeUbicaciones {
    @GET("locaciones-donacion")
    Call<ListadoDeComunidades> comunidadesRecomendadas(@Header("Authorization") String token,@Query("lat") Float latitud, @Query("lon") Float longitud, @Query("limite") Integer max,@Query("distanciaMaxEnKM") Float distanciaMax);
    @GET("key")
    Call<ApiKey> apiKey();
}