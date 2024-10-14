package utils.recomendacioneDeUbicaciones.servicios;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import utils.recomendacioneDeUbicaciones.entidades.ApiKey;
import utils.recomendacioneDeUbicaciones.entidades.ListadoDeComunidades;

import java.io.IOException;

public class RecomedacionDeUbicaciones {
    private static RecomedacionDeUbicaciones instancia = null;
    private Retrofit retrofit;
    private static String urlApi = "http://localhost:3000/api/";

    // ---------------------------------------------------------------- //

    private RecomedacionDeUbicaciones(){
        this.retrofit = new Retrofit.Builder()
                .baseUrl(urlApi)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    // ---------------------------------------------------------------- //

    public static RecomedacionDeUbicaciones getInstance(){
        if(instancia == null){
            instancia = new RecomedacionDeUbicaciones();
        }
        return instancia;
    }
    public ApiKey apiKey()throws IOException{
        IRecomendacionDeUbicaciones recomendacionDeUbicaciones =
                this.retrofit.create(IRecomendacionDeUbicaciones.class);

        Call<ApiKey> request = recomendacionDeUbicaciones.apiKey();
        Response<ApiKey> response = request.execute();
        return response.body();
    }
    public ListadoDeComunidades listadoCumunidades(String token,Float latitud, Float longitud, Integer max, Float distanciaMax) throws IOException {
        IRecomendacionDeUbicaciones recomendacionDeUbicaciones =
                this.retrofit.create(IRecomendacionDeUbicaciones.class);
        System.out.println(token);
        System.out.println(latitud);
        System.out.println(max);
        System.out.println(distanciaMax);
        Call<ListadoDeComunidades> request = recomendacionDeUbicaciones.comunidadesRecomendadas(token,latitud,longitud,max,distanciaMax);
        Response<ListadoDeComunidades> response = request.execute();
        System.out.println(response);
        System.out.println(response.headers());
        return response.body();
    } // Llama a la API e intenta 'matchear' con mi clase molde.
}
