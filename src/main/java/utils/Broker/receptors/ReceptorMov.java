package utils.Broker.receptors;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import domain.heladera.Heladera.Heladera;
import domain.heladera.Sensores.SensorMovimiento;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import repositorios.interfaces.IRepositorioHeladeras;

import java.util.Optional;

public class ReceptorMov extends Receptor {
    private IRepositorioHeladeras repositorioHeladeras;
    public ReceptorMov(IRepositorioHeladeras repositorioHeladeras) {
        this.repositorioHeladeras = repositorioHeladeras;
    }

    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) {
        JsonObject jsonObject = getJsonObjectFrom(mqttMessage);
        Integer idHeladera = Integer.parseInt(jsonObject.get("id").getAsString());
        Optional<Heladera> heladera = repositorioHeladeras.obtenerHeladeraPorID(idHeladera); //Se obtiene a la heladera ,y al ser un objeto no es necesario hacer una actualización para la persitencia en memoria.
        if(heladera.isPresent()){
            System.out.println("Mensaje recibido del topic "+ topic + ": "+ mqttMessage);
            SensorMovimiento sensorMovimiento = heladera.get().getSensorMovimiento();
            sensorMovimiento.recibirMovimientoDetectado();
            repositorioHeladeras.actualizar(heladera.get());
        }
    }
}
