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
public class SensorTemperatura {
    private Heladera heladera;

    public void recibirTemperaturaActual(Float dato){
        try {
            heladera.setUltimaTemperaturaRegistrada(dato);
            if(heladera.temperaturaFueraDeRango()){
                heladera.fallaTemperatura();
                System.out.println("Activación de alerta de falla de temperatura!");
            }
        }catch (Exception e){
           System.out.println(e.getMessage());
        }
    }
}
