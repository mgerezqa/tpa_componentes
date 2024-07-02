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
import utils.Broker.ServiceBroker;

@Setter @Getter
public class SensorTemperatura implements IMqttMessageListener {
    private Heladera heladera;
    private Float temperaturaMax;
    private Float temperaturaMin;

    public void setearTemperaturasMaxMin(){
        temperaturaMax = heladera.getModelo().getTemperaturaMaxima();
        temperaturaMin = heladera.getModelo().getTemperaturaMinima();
    }
    public void recibirTemperaturaActual(Float dato){
        heladera.setUltimaTemperaturaRegistrada(dato);
        if(heladera.temperaturaFueraDeRango()){
            heladera.fallaTemperatura();
        }
    }
    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) {
        Gson gson = new Gson();
        String jsonString = mqttMessage.toString();
        JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);
        Integer idHeladera = Integer.parseInt(jsonObject.get("id_heladera").getAsString());
        Float temperaturaActual = Float.parseFloat(jsonObject.get("temperatura").getAsString());
        if(heladera.getId().equals(idHeladera)){
            this.recibirTemperaturaActual(temperaturaActual);
        }
    }
}
