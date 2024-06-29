package utils.recomendacionDePuntos.Servicios;
import utils.recomendacionDePuntos.Entidades.ListadoDePuntos;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

public class ServicioRecomendacionDePuntos {

    private static ServicioRecomendacionDePuntos instancia = null;
    private Retrofit retrofit;
    private static String urlApi = "https://27eb376e-1830-44e7-8383-fddafec9e810.mock.pstmn.io/api/";

    // ---------------------------------------------------------------- //

    private ServicioRecomendacionDePuntos(){
        this.retrofit = new Retrofit.Builder()
                .baseUrl(urlApi)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    } // Estoy instanciando RETROFIT para poder utilizarlo.

    // ---------------------------------------------------------------- //

    public static ServicioRecomendacionDePuntos getInstance(){
        if(instancia == null){
            instancia = new ServicioRecomendacionDePuntos();
        }
        return instancia;
    }

    public ListadoDePuntos listadoDePuntos(Double latitud, Double longitud, Double radio) throws IOException {
        ServicioRecomendacionDePuntosInterface recomendacionDePuntos =
            this.retrofit.create(ServicioRecomendacionDePuntosInterface.class);

        Call<ListadoDePuntos> requestPuntosRecomendados = recomendacionDePuntos.puntosRecomendados(latitud,longitud,radio);
        Response<ListadoDePuntos> responsePuntosRecomendados = requestPuntosRecomendados.execute();
        return responsePuntosRecomendados.body();
    } // Llama a la API e intenta 'matchear' con mi clase molde.

}
