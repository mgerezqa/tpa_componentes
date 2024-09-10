package utils.Broker.receptors;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import domain.heladera.Heladera.Heladera;
import domain.heladera.Sensores.SensorTemperatura;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import repositorios.Repositorio;
import repositorios.interfaces.IRepositorioHeladeras;

import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ReceptorTemp extends Receptor implements WithSimplePersistenceUnit {
    //private IRepositorioHeladeras repositorioHeladeras;
    private Repositorio repositorioHeladeras;
    public ReceptorTemp(Repositorio repositorioHeladeras) {
        this.repositorioHeladeras = repositorioHeladeras;
    }
    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) {
        System.out.println(mqttMessage);
        JsonObject jsonObject = getJsonObjectFrom(mqttMessage);
        Long idHeladera = Long.parseLong(jsonObject.get("id").getAsString());
        String temperatura = jsonObject.get("temp").getAsString();
        Optional<Object> heladera = repositorioHeladeras.buscarPorID(Heladera.class,idHeladera);
        if(heladera.isPresent()){
            System.out.println("Mensaje recibido del topic "+ topic + ": "+ mqttMessage);
            Heladera heladeraEncontrada = (Heladera) heladera.get();
            SensorTemperatura sensorTemperatura = new SensorTemperatura(heladeraEncontrada);
            System.out.println(heladeraEncontrada);
            System.out.println(sensorTemperatura);
            sensorTemperatura.recibirTemperaturaActual(temperatura);
            System.out.println(heladeraEncontrada);
            withTransaction(() -> {
                System.out.println("Llega al transaction");
                repositorioHeladeras.actualizar(heladeraEncontrada);
            });
            System.out.println(heladeraEncontrada.getIncidentes());
            System.out.println(heladeraEncontrada.getUltimaTemperaturaRegistrada());
        }
    }
}

