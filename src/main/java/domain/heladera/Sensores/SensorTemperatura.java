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

import java.time.LocalDateTime;

@Setter @Getter
public class SensorTemperatura {

    private Heladera heladera;
    private Float temperaturaMax;
    private Float temperaturaMin;

    public SensorTemperatura(Heladera heladera) {
        this.heladera = heladera;
    }

    // ============================================================ //
    // MéTODOS //
    // ============================================================ //

    public void recibirTemperaturaActual(String dato){
        // El sensor fisico nos envia la temperatura sensada.
        // Nosotros como sistema debemos ser capaces de "recibirla".
        // Se recibe el dato en un "string" que bien podria ser un archivo json u otro.
        Float temperaturaActual = Float.parseFloat(dato);
        //heladera.setUltimaTemperaturaRegistrada(dato);
        heladera.setUltimaTemperaturaRegistrada(temperaturaActual, LocalDateTime.now());
        if(heladera.temperaturaFueraDeRango()){
            heladera.fallaTemperatura();
            System.out.println("Activación de alerta de falla de temperatura!");
        }
    }
}
