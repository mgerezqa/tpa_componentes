package utils.Broker;

import domain.Config;
import domain.geografia.Calle;
import domain.geografia.Ubicacion;
import domain.heladera.Heladera.Heladera;
import domain.heladera.Heladera.ModeloDeHeladera;
import domain.heladera.Sensores.SensorMovimiento;
import domain.heladera.Sensores.SensorTemperatura;
import domain.usuarios.ColaboradorFisico;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import repositorios.interfaces.IRepositorioColaboradores;
import repositorios.interfaces.IRepositorioHeladeras;
import repositorios.reposEnMemoria.RepositorioColaboradores;
import repositorios.reposEnMemoria.RepositorioHeladeras;
import utils.Broker.receptors.ReceptorAutorizacion;
import utils.Broker.receptors.ReceptorMov;
import utils.Broker.receptors.ReceptorTemp;

import java.util.Locale;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class Test2 {
    public static void main(String[] args) {
        SensorMovimiento sensorMovimiento;
        SensorTemperatura sensorTemperatura;
        ModeloDeHeladera modeloHeladera;
        Ubicacion ubicacion;
        IRepositorioHeladeras repositorioHeladeras;
        IRepositorioColaboradores repositorioColaboradores;
        Heladera heladera;
        ColaboradorFisico colaborador;
        //Configuración de repositorios,heladera, etc.
        repositorioHeladeras = new RepositorioHeladeras();
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
        heladera.setId(1); //Seteo el id ya que la persistencia es en memoria.
        repositorioHeladeras.darDeAlta(heladera);
        repositorioColaboradores = new RepositorioColaboradores();
        colaborador = new ColaboradorFisico("Lalo", "Menz");
        colaborador.setId(1L);
        repositorioColaboradores.alta(colaborador);
        //Configuración de conexión con el broker, topics.
        String topic = "dds2024/heladera/autorizacion";
        String broker = "tcp://freemqttbroker.sfodo.crystalmq.com:1883";
        IMqttMessageListener receptor = new ReceptorAutorizacion(repositorioHeladeras, repositorioColaboradores);

        //Acceso al config para traer las credentials, martin realizo algo, ver.

        Config config = Config.getInstance();
        ClientCredentials credentials = new ClientCredentials(config.getProperty("broker.clientID"),config.getProperty("broker.clientUsername"),config.getProperty("broker.clientPassword"));
        IServiceBroker serviceBroker = new ServiceBroker(broker,credentials);

        String msg = "{'idH': 1, 'idC':1}";

        serviceBroker.init();

        serviceBroker.suscribe(topic, receptor);


        serviceBroker.publishMessage(topic, msg);
        System.out.println(heladera.getSolicitudesPendientes());
    }
}
