package domain.heladera.Sensores;
import domain.heladera.Heladera;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class SensorMovimiento {

    private Heladera heladera;

    // TODO
    public void detectoMovimientoExtranio(){
        heladera.alertarAlSistema();
    }

}
