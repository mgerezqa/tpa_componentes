package models.entities.geografia.RecomendacionDePuntos.Servicios;
import models.entities.geografia.RecomendacionDePuntos.Entidades.ListadoDePuntos;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ServicioRecomendacionDePuntosInterface {
    @GET("recomendacionespuntos")
    Call<ListadoDePuntos> puntosRecomendados(@Query("latitud") Double latitud, @Query("longitud") Double longitud, @Query("radio") Double radio);
}
