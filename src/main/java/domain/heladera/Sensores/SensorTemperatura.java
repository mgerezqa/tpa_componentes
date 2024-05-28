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

    // TODO (logica no especificada)
    public Float recibirTemperaturaActual(){
        // El sensor fisico nos envia la temperatura sensada.
        // Nosotros como sistema debemos ser capaces de "recibirla".
        return 0f;
    }

    public Boolean problemaDeTemperatura(){
        float temperaturaActual = this.recibirTemperaturaActual();
        return ((temperaturaActual > temperaturaMax) || (temperaturaActual < temperaturaMin));
    }

}
