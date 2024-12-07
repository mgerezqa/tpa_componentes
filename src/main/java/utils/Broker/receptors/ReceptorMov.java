package utils.Broker.receptors;

import com.google.gson.JsonObject;
import domain.heladera.Heladera.Heladera;
import domain.heladera.Sensores.SensorMovimiento;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import repositorios.Repositorio;

import java.util.Optional;

public class ReceptorMov extends Receptor implements WithSimplePersistenceUnit {
    private Repositorio repositorioHeladeras;
    public ReceptorMov(Repositorio repositorioHeladeras) {
        this.repositorioHeladeras = repositorioHeladeras;
    }

    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) {
        entityManager().clear();
        JsonObject jsonObject = getJsonObjectFrom(mqttMessage);
        Long idHeladera = Long.parseLong(jsonObject.get("id").getAsString());
        Optional<Object> heladera = repositorioHeladeras.buscarPorID(Heladera.class,idHeladera); //Se obtiene a la heladera ,y al ser un objeto no es necesario hacer una actualización para la persitencia en memoria.
        if(heladera.isPresent()){
            System.out.println("Mensaje recibido del topic "+ topic + ": "+ mqttMessage);
            Heladera heladeraEncontrada = (Heladera) heladera.get();
            System.out.println(heladeraEncontrada);
            SensorMovimiento sensorMovimiento = new SensorMovimiento(heladeraEncontrada);
            sensorMovimiento.recibirMovimientoDetectado();
            System.out.println(heladeraEncontrada);
            withTransaction(() -> {
                repositorioHeladeras.actualizar(heladeraEncontrada);
            });
        }
    }
}
