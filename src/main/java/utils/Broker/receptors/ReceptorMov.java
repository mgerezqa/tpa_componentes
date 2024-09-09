package utils.Broker.receptors;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import domain.heladera.Heladera.Heladera;
import domain.heladera.Sensores.SensorMovimiento;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import repositorios.interfaces.IRepositorioHeladeras;
import repositorios.reposEnMemoria.RepositorioHeladeras;

import java.util.Optional;

public class ReceptorMov implements IMqttMessageListener {
    private RepositorioHeladeras repositorioHeladeras;
    public ReceptorMov(RepositorioHeladeras repositorioHeladeras) {
        this.repositorioHeladeras = repositorioHeladeras;
    }

    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) {
        Gson gson = new Gson();
        String jsonString = mqttMessage.toString();
        JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);
        Integer idHeladera = Integer.parseInt(jsonObject.get("id").getAsString());
        Heladera heladera = repositorioHeladeras.obtenerHeladeraPorID(idHeladera.toString()); //Se obtiene a la heladera ,y al ser un objeto no es necesario hacer una actualización para la persitencia en memoria.
        if(heladera != null ){
            System.out.println("Mensaje recibido del topic "+ topic + ": "+ mqttMessage);
            SensorMovimiento sensorMovimiento = heladera.getSensorMovimiento();
            sensorMovimiento.recibirMovimientoDetectado();
        }
    }
}
