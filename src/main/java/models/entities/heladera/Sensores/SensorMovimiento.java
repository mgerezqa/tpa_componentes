package models.entities.heladera.Sensores;
import models.entities.heladera.Heladera.Heladera;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class SensorMovimiento {

    private Heladera heladera;

    // TODO (logica no especificada)
    public void recibirAlertaPorMovimientoDetectado(){

        // A traves de este metodo, nuestro sistema es capaz de recibir
        // una alerta del sensor fisico.

        heladera.alertaDetectada();
    }

}