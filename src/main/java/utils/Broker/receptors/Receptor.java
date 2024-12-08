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
    public JsonObject getJsonObjectFrom(MqttMessage mqttMessage){
        Gson gson = new Gson();
        String jsonString = mqttMessage.toString();
        return gson.fromJson(jsonString, JsonObject.class);
    }
}
