package utils.Broker.receptors;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import domain.heladera.Heladera.Heladera;
import domain.heladera.Sensores.SensorTemperatura;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import repositorios.repositoriosBDD.RepositorioHeladeras;

public class ReceptorTemp implements IMqttMessageListener {
    private RepositorioHeladeras repositorioHeladeras;
    public ReceptorTemp(RepositorioHeladeras repositorioHeladeras) {
        this.repositorioHeladeras = repositorioHeladeras;
    }
    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) {
        Gson gson = new Gson();
        String jsonString = mqttMessage.toString();
        JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);
        Integer idHeladera = Integer.parseInt(jsonObject.get("id").getAsString());
        String temperatura = jsonObject.get("temp").getAsString();
        Heladera heladera = repositorioHeladeras.obtenerHeladeraPorID(idHeladera.toString());
        if(heladera != null){
            System.out.println("Mensaje recibido del topic "+ topic + ": "+ mqttMessage);
            SensorTemperatura sensorTemperatura = heladera.getSensorTemperatura();
            sensorTemperatura.recibirTemperaturaActual(temperatura);
        }
    }
}

