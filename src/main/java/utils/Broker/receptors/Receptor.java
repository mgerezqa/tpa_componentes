package utils.Broker.receptors;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import domain.heladera.Heladera.Heladera;
import domain.heladera.Sensores.SensorMovimiento;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import repositorios.interfaces.IRepositorioHeladeras;
import java.util.Optional;

public abstract class Receptor implements IMqttMessageListener {
    private IRepositorioHeladeras repositorioHeladeras; // Esto es mejor que recibir las heladeras directamente, ya que el repositorio nos proporciona la manera de buscar por id.

    public Receptor(IRepositorioHeladeras repositorioHeladeras) {
        this.repositorioHeladeras = repositorioHeladeras;
    }
    public JsonObject getJsonObjectFrom(MqttMessage mqttMessage){
        Gson gson = new Gson();
        String jsonString = mqttMessage.toString();
        return gson.fromJson(jsonString, JsonObject.class);
    }
    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) {
        JsonObject jsonObject = this.getJsonObjectFrom(mqttMessage);
        Integer idHeladera = Integer.parseInt(jsonObject.get("id_heladera").getAsString());
        Optional<Heladera> heladera = repositorioHeladeras.obtenerHeladeraPorID(idHeladera);
        if(heladera.isPresent()){
            SensorMovimiento sensorMovimiento = heladera.get().getSensorMovimiento();
            sensorMovimiento.recibirMovimientoDetectado();
        }
    }

}
