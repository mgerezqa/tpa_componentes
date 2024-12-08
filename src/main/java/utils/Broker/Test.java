package utils.Broker;

import config.ServiceLocator;
import domain.geografia.Calle;
import domain.geografia.Ubicacion;
import domain.heladera.Heladera.Heladera;
import domain.heladera.Heladera.ModeloDeHeladera;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import repositorios.Repositorio;

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
        ServiceBroker serviceBroker = ServiceLocator.instanceOf(ServiceBroker.class); //Aca se ve la ventaja de ahora es un una linea de codigo, por usar el service locator y tener toda la logica de instanciación ahi metida.

        //Parte para generar simulación de sensor para enviar temperaturas
        String topic1 = "dds2024/heladera/movimiento";
        String topic2 = "dds2024/heladera/temperatura";
        Timer timer = new Timer();
        long delay = 0;
        long intervalPeriod = 10000;
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
