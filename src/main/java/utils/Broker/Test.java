package utils.Broker;

import domain.Config;
import domain.geografia.Calle;
import domain.geografia.Ubicacion;
import domain.heladera.Heladera.Heladera;
import domain.heladera.Heladera.ModeloDeHeladera;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import repositorios.Repositorio;
import utils.Broker.receptors.ReceptorMov;
import utils.Broker.receptors.ReceptorTemp;

import java.util.Locale;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class Test implements WithSimplePersistenceUnit {
    ModeloDeHeladera modeloHeladera;
    Ubicacion ubicacion;
    Repositorio repositorio;
    Heladera heladera;
    public static void main(String[] args) {
        Test instance = new Test();
        //Configuración de repositorios,heladera, etc.
        instance.repositorio = new Repositorio();
        instance.init();
    }
    void init(){
        modeloHeladera = new ModeloDeHeladera("Modelo X-R98");
        ubicacion = new Ubicacion(-34.5986317f,-58.4212435f,new Calle("Av Medrano", "951"));
        heladera = new Heladera(modeloHeladera,"Medrano",ubicacion);

        heladera.darDeAltaHeladera();
        modeloHeladera.setTemperaturaMaxima(60.4f);
        modeloHeladera.setTemperaturaMinima(80.4f);

        withTransaction(() -> {
            repositorio.guardar(heladera);
        });

        //Configuración de conexión con el broker, topics.
        String topic1 = "dds2024/heladera/movimiento";
        String topic2 = "dds2024/heladera/temperatura";
        String broker = "tcp://freemqttbroker.sfodo.crystalmq.com:1883";
        IMqttMessageListener receptor1 = new ReceptorMov(repositorio);
        IMqttMessageListener receptor2 = new ReceptorTemp(repositorio);

        Config config = Config.getInstance();
        ClientCredentials credentials = new ClientCredentials(config.getProperty("broker.clientID"),config.getProperty("broker.clientUsername"),config.getProperty("broker.clientPassword"));
        IServiceBroker serviceBroker = new ServiceBroker(broker,credentials);

        serviceBroker.init();

        serviceBroker.suscribe(topic1, receptor1);
        serviceBroker.suscribe(topic2, receptor2);


        Timer timer = new Timer();
        long delay = 0;
        long intervalPeriod = 3000;
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                float min = 50f;
                float max = 90f;
                Random random = new Random();
                float temperature = min + (max - min) * random.nextFloat();
                String msg1 = "{'id': 1}";
                String msg2 = String.format(Locale.US,"{'id':1,'temp':%.2f}",temperature);
                serviceBroker.publishMessage(topic1, msg1);
                serviceBroker.publishMessage(topic2, msg2);
            }
        };
        timer.scheduleAtFixedRate(task, delay, intervalPeriod);
    }

}
