package utils.Broker;

import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import utils.Broker.receptors.ReceptorTemp;

public class ServiceBroker implements IServiceBroker {
    String broker;
    String clientId;
    Integer qos = 1;
    Boolean retain = false;
    MemoryPersistence persistence;
    MqttClient sampleClient;
    MqttConnectOptions connOpts;
    Boolean AUTH = true;
    String username;
    String password;


    public ServiceBroker(String broker, ClientCredentials credentials) {
        this.broker = broker;
        this.persistence = new MemoryPersistence();
        this.username = credentials.getUsername();
        this.password = credentials.getPassword();
        this.clientId = credentials.getClientID();
    }
    @Override
    public void init(){
        try {
            sampleClient = new MqttClient(broker, clientId, persistence);
            connOpts = new MqttConnectOptions();
            if (AUTH) {
                connOpts.setUserName(username);
                connOpts.setPassword(password.toCharArray());
                connOpts.setMaxInflight(200);
            }
            connOpts.setCleanSession(true);
            System.out.println("Conectando al broker... "+ broker);
            sampleClient.connect(connOpts);
            System.out.println("Conectado!");

        } catch(MqttException me) {
            System.out.println("reason " + me.getReasonCode());
            System.out.println("msg " + me.getMessage());
        }
    }
    @Override
    public void publishMessage(String topic, String message){
        try {
            sampleClient.publish(topic, message.getBytes(), qos, retain);
        } catch(MqttException me) {
            System.out.println("reason " + me.getReasonCode());
            System.out.println("msg " + me.getMessage());
        }
    }
    @Override
    public void suscribe(String topic, IMqttMessageListener receptor){
        try {
            System.out.println("Suscripción al topic: "+ topic);
            sampleClient.subscribe(topic, qos, receptor);
        } catch(MqttException me) {
            System.out.println("reason " + me.getReasonCode());
            System.out.println("msg " + me.getMessage());
        }
    }
}
