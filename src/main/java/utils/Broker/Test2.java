package utils.Broker;

import domain.Config;
import domain.geografia.Calle;
import domain.geografia.Ubicacion;
import domain.heladera.Heladera.Heladera;
import domain.heladera.Heladera.ModeloDeHeladera;
import domain.usuarios.ColaboradorFisico;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import repositorios.Repositorio;
import utils.Broker.receptors.ReceptorAutorizacion;

public class Test2 implements WithSimplePersistenceUnit {
    ModeloDeHeladera modeloHeladera;
    Ubicacion ubicacion;
    Repositorio repositorio;
    Heladera heladera;
    ColaboradorFisico colaborador;

    public static void main(String[] args) {
        Test2 instance = new Test2();
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
            colaborador = new ColaboradorFisico("Lalo", "Menz");
            repositorio.guardar(colaborador);
        });
        //Configuración de conexión con el broker, topics.
        String topic = "dds2024/heladera/autorizacion";
        String broker = "tcp://freemqttbroker.sfodo.crystalmq.com:1883";
        IMqttMessageListener receptor = new ReceptorAutorizacion(repositorio);

        Config config = Config.getInstance();
        ClientCredentials credentials = new ClientCredentials(config.getProperty("broker.clientID"),config.getProperty("broker.clientUsername"),config.getProperty("broker.clientPassword"));
        IServiceBroker serviceBroker = new ServiceBroker(broker,credentials);

        serviceBroker.init();
        serviceBroker.suscribe(topic, receptor);

        String msg = "{'idH': 1, 'idC':1}";
        serviceBroker.publishMessage(topic, msg);

    }

}
