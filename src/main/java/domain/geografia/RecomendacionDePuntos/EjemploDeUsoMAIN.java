package domain.geografia.RecomendacionDePuntos;
import domain.geografia.RecomendacionDePuntos.Entidades.Calle;
import domain.geografia.RecomendacionDePuntos.Entidades.ListadoDePuntos;
import domain.geografia.RecomendacionDePuntos.Entidades.Ubicacion;
import domain.geografia.RecomendacionDePuntos.Servicios.ServicioRecomendacionDePuntos;
import java.io.IOException;

public class EjemploDeUsoMAIN {

    public static void main(String[] args) throws IOException {
        ServicioRecomendacionDePuntos servicioRecomendacionDePuntos = ServicioRecomendacionDePuntos.getInstance();

        ListadoDePuntos listadoDePuntos = servicioRecomendacionDePuntos.listadoDePuntos();

        for(Ubicacion listadoDePuntos1 : listadoDePuntos.puntosRecomendados){
            System.out.println("\n" + "Latitud:" + listadoDePuntos1.getLatitud() + "\n");
            System.out.println("Longitud:" + listadoDePuntos1.getLongitud() + "\n");
            Calle calle = listadoDePuntos1.getCalle();
            System.out.println("Calle: " + calle.getNombre() + "\n");
            System.out.println("Altura: " + calle.getAltura() + "\n");
            System.out.println(" ------------------------------------------------------ " + "\n");
        }
    }

}
