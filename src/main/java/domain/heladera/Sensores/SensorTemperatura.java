package domain.heladera.Sensores;
import domain.heladera.Heladera.Heladera;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class SensorTemperatura {

    private Heladera heladera;
    private Float temperaturaMax;
    private Float temperaturaMin;

    public void setearTemperaturasMaxMin(){
        temperaturaMax = heladera.getModelo().getTemperaturaMaxima();
        temperaturaMin = heladera.getModelo().getTemperaturaMinima();
    }

    // TODO (lógica no especificada)
    public Float recibirTemperaturaActual(String dato){

        // El sensor fisico nos envia la temperatura sensada.
        // Nosotros como sistema debemos ser capaces de "recibirla".
        // Se recibe el dato en un "string" que bien podria ser un archivo json u otro.
        // ACA HABRIA QUE SETEAR LA TEMPERATURA ACTUAL DE LA HELADERA. Float temperaturaActual

        return 0f;
    }

}
