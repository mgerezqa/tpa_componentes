package domain.heladera;
import lombok.Setter;

public class SensorTemperatura {

    @Setter
    private Float temperaturaMax;
    @Setter
    private Float temperaturaMin;

    public float leerTemperatura(){
        return 0;
    }


}
