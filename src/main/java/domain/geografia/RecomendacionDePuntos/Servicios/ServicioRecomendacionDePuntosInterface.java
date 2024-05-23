package domain.geografia.RecomendacionDePuntos.Servicios;
import domain.geografia.RecomendacionDePuntos.Entidades.ListadoDePuntos;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ServicioRecomendacionDePuntosInterface {
    @GET("recomendacionespuntos")
    Call<ListadoDePuntos> puntosRecomendados();
}
