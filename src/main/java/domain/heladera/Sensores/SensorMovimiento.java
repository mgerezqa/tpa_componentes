package domain.heladera.Sensores;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import domain.heladera.Heladera.Heladera;
import domain.incidentes.Alerta;
import domain.incidentes.IncidenteFactory;
import lombok.Getter;
import lombok.Setter;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;

@Setter @Getter
public class SensorMovimiento implements IMqttMessageListener {
    private Heladera heladera;

    public void recibirAlertaPorMovimientoDetectado(){
        heladera.alertaFraude();
    }
    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) {
        Gson gson = new Gson();
        String jsonString = mqttMessage.toString();
        JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);
        Integer idHeladera = Integer.parseInt(jsonObject.get("id_heladera").getAsString());
        if(heladera.getId().equals(idHeladera)){
            this.recibirAlertaPorMovimientoDetectado();
        }
    }
}
