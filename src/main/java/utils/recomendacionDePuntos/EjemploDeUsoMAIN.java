package utils.recomendacionDePuntos;
import utils.recomendacionDePuntos.Entidades.ListadoDePuntos;
import utils.recomendacionDePuntos.Entidades.Punto;
import utils.recomendacionDePuntos.Servicios.ServicioRecomendacionDePuntos;
import java.io.IOException;

public class EjemploDeUsoMAIN {

    public static void main(String[] args) throws IOException {
        ServicioRecomendacionDePuntos servicioRecomendacionDePuntos = ServicioRecomendacionDePuntos.getInstance();

        Double latitud = -11.9987;
        Double longitud = -12.7899;
        Double radio = 10.0;

        ListadoDePuntos listadoDePuntos = servicioRecomendacionDePuntos.listadoDePuntos(latitud, longitud,radio);

        for(Punto puntoRecomendado : listadoDePuntos.getPuntosRecomendados()) {
            System.out.println("Latitud: " + puntoRecomendado.getLatitud() + ", Longitud: " + puntoRecomendado.getLongitud());
        }

    }

}
