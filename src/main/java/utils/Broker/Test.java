package utils.Broker;

import domain.Config;
import domain.geografia.Calle;
import domain.geografia.Ubicacion;
import domain.heladera.Heladera.Heladera;
import domain.heladera.Heladera.ModeloDeHeladera;
import domain.heladera.Sensores.SensorMovimiento;
import domain.heladera.Sensores.SensorTemperatura;
import domain.puntos.CategoriaOferta;
import domain.puntos.Oferta;
import domain.usuarios.ColaboradorJuridico;
import domain.usuarios.Rubro;
import domain.usuarios.TipoRazonSocial;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import main.OfertasTest;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import repositorios.Repositorio;
import repositorios.interfaces.IRepositorioHeladeras;
import repositorios.reposEnMemoria.RepositorioHeladeras;
import utils.Broker.receptors.ReceptorMov;
import utils.Broker.receptors.ReceptorTemp;

import java.util.Locale;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class Test implements WithSimplePersistenceUnit {
    SensorMovimiento sensorMovimiento;
    SensorTemperatura sensorTemperatura;
    ModeloDeHeladera modeloHeladera;
    Ubicacion ubicacion;
    IRepositorioHeladeras repositorioHeladeras;
    Repositorio repositorio;
    Heladera heladera;
    public static void main(String[] args) {
        Test instance = new Test();
        //Configuración de repositorios,heladera, etc.
        instance.repositorioHeladeras = new RepositorioHeladeras();
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
        sensorMovimiento = new SensorMovimiento(heladera);
        heladera.setSensorMovimiento(sensorMovimiento);
        sensorTemperatura = new SensorTemperatura(heladera);
        heladera.setSensorTemperatura(sensorTemperatura);
        //heladera.setId(10); //Seteo el id ya que la persistencia es en memoria.
        //repositorioHeladeras.darDeAlta(heladera);
        withTransaction(() -> {
            repositorio.guardar(heladera);
        });
        repositorio.guardar(heladera);
        //Configuración de conexión con el broker, topics.
        //String topic1 = "dds2024/heladera/movimiento";
        String topic2 = "dds2024/heladera/temperatura";
        String broker = "tcp://freemqttbroker.sfodo.crystalmq.com:1883";
        //IMqttMessageListener receptor1 = new ReceptorMov(repositorioHeladeras);
        IMqttMessageListener receptor2 = new ReceptorTemp(repositorio);

        //Acceso al config para traer las credentials, martin realizo algo, ver.

        Config config = Config.getInstance();
        ClientCredentials credentials = new ClientCredentials(config.getProperty("broker.clientID"),config.getProperty("broker.clientUsername"),config.getProperty("broker.clientPassword"));
        IServiceBroker serviceBroker = new ServiceBroker(broker,credentials);

        String msg1 = "{'id': 10}";

        serviceBroker.init();

        //serviceBroker.suscribe(topic1, receptor1);
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
                String msg2 = String.format(Locale.US,"{'id':1,'temp':%.2f}",temperature);
                serviceBroker.publishMessage(topic2, msg2);
                //serviceBroker.publishMessage(topic1, msg1);
            }
        };
        timer.scheduleAtFixedRate(task, delay, intervalPeriod);
    }

}
