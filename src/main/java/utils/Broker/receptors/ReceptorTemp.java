package utils.Broker.receptors;

import com.google.gson.JsonObject;
import domain.heladera.Heladera.Heladera;
import domain.heladera.Sensores.SensorTemperatura;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import repositorios.Repositorio;
import repositorios.repositoriosBDD.RepositorioTecnicos;
import utils.asignadorTecnicos.AsignadorDeTecnico;

import java.util.Optional;


public class ReceptorTemp extends Receptor implements WithSimplePersistenceUnit {
    private Repositorio repositorioHeladeras;
    public ReceptorTemp(Repositorio repositorioHeladeras) {
        this.repositorioHeladeras = repositorioHeladeras;
    }
    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) {
        entityManager().clear();
        JsonObject jsonObject = getJsonObjectFrom(mqttMessage);
        Long idHeladera = Long.parseLong(jsonObject.get("id").getAsString());
        String temperatura = jsonObject.get("temp").getAsString();
        Optional<Object> heladera = repositorioHeladeras.buscarPorID(Heladera.class,idHeladera);
        if(heladera.isPresent()){
            System.out.println("Mensaje recibido del topic "+ topic + ": "+ mqttMessage);
            Heladera heladeraEncontrada = (Heladera) heladera.get();
            SensorTemperatura sensorTemperatura = new SensorTemperatura(heladeraEncontrada);
            sensorTemperatura.recibirTemperaturaActual(temperatura);
            withTransaction(() -> {
                repositorioHeladeras.actualizar(heladeraEncontrada);
            });
        }
    }
}

