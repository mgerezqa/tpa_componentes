package domain.geografia.RecomendacionDePuntos;
import domain.geografia.Calle;
import domain.geografia.RecomendacionDePuntos.Entidades.ListadoDePuntos;
import domain.geografia.RecomendacionDePuntos.Entidades.Punto;
import domain.geografia.Ubicacion;
import domain.geografia.RecomendacionDePuntos.Servicios.ServicioRecomendacionDePuntos;
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
