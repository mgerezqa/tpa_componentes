package utils.Broker;

import domain.Config;
import domain.geografia.Calle;
import domain.geografia.Ubicacion;
import domain.heladera.Heladera.Heladera;
import domain.heladera.Heladera.ModeloDeHeladera;
import domain.heladera.Sensores.SensorMovimiento;
import domain.heladera.Sensores.SensorTemperatura;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import repositorios.interfaces.IRepositorioHeladeras;
import repositorios.reposEnMemoria.RepositorioHeladeras;
import utils.Broker.receptors.ReceptorMov;
import utils.Broker.receptors.ReceptorTemp;

import java.util.Locale;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class Test {
    public static void main(String[] args) {
        SensorMovimiento sensorMovimiento;
        SensorTemperatura sensorTemperatura;
        ModeloDeHeladera modeloHeladera;
        Ubicacion ubicacion;
        IRepositorioHeladeras repositorioHeladeras;
        Heladera heladera;
        //Configuración de repositorios,heladera, etc.
        repositorioHeladeras = new RepositorioHeladeras();
        modeloHeladera = new ModeloDeHeladera("Modelo X-R98",60.4f,80.4f);
        ubicacion = new Ubicacion(-34.5986317f,-58.4212435f,new Calle("Av Medrano", "951"));
        heladera = new Heladera(modeloHeladera,"Medrano",ubicacion);
        heladera.darDeAltaHeladera();
        sensorMovimiento = new SensorMovimiento();
        sensorMovimiento.setHeladera(heladera);
        heladera.setSensorMovimiento(sensorMovimiento);
        sensorTemperatura = new SensorTemperatura();
        sensorTemperatura.setHeladera(heladera);
        heladera.setSensorTemperatura(sensorTemperatura);
        heladera.setId(10); //Seteo el id ya que la persistencia es en memoria.
        repositorioHeladeras.darDeAlta(heladera);
        //Configuración de conexión con el broker, topics.
        String topic1 = "dds2024/heladera/movimiento";
        String topic2 = "dds2024/heladera/temperatura";
        String broker = "tcp://freemqttbroker.sfodo.crystalmq.com:1883";
        IMqttMessageListener receptor1 = new ReceptorMov(repositorioHeladeras);
        IMqttMessageListener receptor2 = new ReceptorTemp(repositorioHeladeras);

        //Acceso al config para traer las credentials, martin realizo algo, ver.
        try {
            Config.init();
        } catch (Exception ex){
            System.out.println(ex.getMessage());
        }

        ClientCredentials credentials = new ClientCredentials(Config.getProperty("broker.clientID"),Config.getProperty("broker.clientUsername"),Config.getProperty("broker.clientPassword"));
        IServiceBroker serviceBroker = new ServiceBroker(broker,credentials);

        String msg1 = "{'id': 10}";

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
                String msg2 = String.format(Locale.US,"{'id':10,'temp':%.2f}",temperature);
                serviceBroker.publishMessage(topic2, msg2);
                serviceBroker.publishMessage(topic1, msg1);
                System.out.println(heladera.getIncidentes());
                System.out.println(heladera.getUltimaTemperaturaRegistrada());
            }
        };
        timer.scheduleAtFixedRate(task, delay, intervalPeriod);
    }
}
