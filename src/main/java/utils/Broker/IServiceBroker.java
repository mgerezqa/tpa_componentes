package utils.Broker;

import org.eclipse.paho.client.mqttv3.IMqttMessageListener;

public interface IServiceBroker {
    void init();
    void publishMessage(String topic, String message);
    void suscribe(String topic, IMqttMessageListener receptor);
}
