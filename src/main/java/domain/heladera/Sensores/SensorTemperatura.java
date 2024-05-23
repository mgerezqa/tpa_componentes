package domain.heladera.Sensores;
import domain.heladera.Heladera;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class SensorTemperatura {

    private Heladera heladera;
    private Float temperaturaMax;
    private Float temperaturaMin;

    public void setearTemperaturasMaxMin(){
        temperaturaMax = heladera.getTemperaturaMax();
        temperaturaMin = heladera.getTemperaturaMin();
    }

    // TODO
    public Float sensarTemperaturaActual(){
        return 0f;
    }

    public Boolean problemaDeTemperatura(){
        float temperaturaActual = this.sensarTemperaturaActual();
        return ((temperaturaActual > temperaturaMax) || (temperaturaActual < temperaturaMin));
    }

}
