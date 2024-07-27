package utils.Broker.receptors;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import domain.heladera.Heladera.Heladera;
import domain.heladera.Sensores.SensorTemperatura;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import repositorios.interfaces.IRepositorioHeladeras;

import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ReceptorTemp implements IMqttMessageListener {
    private IRepositorioHeladeras repositorioHeladeras;
    public ReceptorTemp(IRepositorioHeladeras repositorioHeladeras) {
        this.repositorioHeladeras = repositorioHeladeras;
    }
    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) {
        Gson gson = new Gson();
        String jsonString = mqttMessage.toString();
        JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);
        Integer idHeladera = Integer.parseInt(jsonObject.get("id").getAsString());
        Float temperatura = Float.parseFloat(jsonObject.get("temp").getAsString());
        Optional<Heladera> heladera = repositorioHeladeras.obtenerHeladeraPorID(idHeladera);
        if(heladera.isPresent()){
            System.out.println("Mensaje recibido del topic "+ topic + ": "+ mqttMessage);
            SensorTemperatura sensorTemperatura = heladera.get().getSensorTemperatura();
            sensorTemperatura.recibirTemperaturaActual(temperatura);
        }
    }
}

