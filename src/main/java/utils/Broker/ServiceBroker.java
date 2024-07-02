package utils.Broker;

import domain.heladera.Sensores.SensorMovimiento;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import repositorios.interfaces.IRepositorioIncidentes;

import java.util.ArrayList;
import java.util.List;

public class ServiceBroker {
    private IRepositorioIncidentes repositorioIncidentes;
    private List<SensorMovimiento> sensores;
    private String topic;
    String broker = "tcp://broker.hivemq.com:1883";
    String clientId = "JavaSample";
    MemoryPersistence persistence = new MemoryPersistence();

    public ServiceBroker(String topic,IRepositorioIncidentes repositorioIncidentes) {
        this.topic = topic;
        this.repositorioIncidentes = repositorioIncidentes;
        this.sensores = new ArrayList<>();
    }
    public void agregarSensores(SensorMovimiento sensor){
        this.sensores.add(sensor);
    }
    public void init(){
        try {
            MqttClient sampleClient = new MqttClient(broker, clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            sampleClient.connect(connOpts);
            for (SensorMovimiento sensor:sensores) {
                System.out.println("Now we subscribe to the topic");
                sampleClient.subscribe(topic, sensor);
                System.out.println("Right! We are subscribed");
            }
        } catch(MqttException me) {
            System.out.println("reason " + me.getReasonCode());
            System.out.println("msg " + me.getMessage());
            System.out.println("loc " + me.getLocalizedMessage());
            System.out.println("cause " + me.getCause());
            System.out.println("excep " + me);
            me.printStackTrace();
        }
    }
}
