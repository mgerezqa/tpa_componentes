package utils.Broker;

import domain.geografia.Calle;
import domain.geografia.Ubicacion;
import domain.heladera.Heladera.Heladera;
import domain.heladera.Heladera.ModeloDeHeladera;
import domain.heladera.Sensores.SensorMovimiento;
import domain.heladera.Sensores.SensorTemperatura;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import repositorios.interfaces.IRepositorioIncidentes;


public class Test {
    ServiceBroker serviceBroker;
    SensorMovimiento sensorMovimiento;
    //SensorTemperatura sensorTemperatura;
    ModeloDeHeladera modeloHeladera;
    Ubicacion ubicacion;
    IRepositorioIncidentes repositorioIncidentes;
    Heladera heladera;

    public void init(){
        serviceBroker = new ServiceBroker("/heladera/movimiento",repositorioIncidentes);
        //serviceBroker = new ServiceBroker("/heladera/temperatura",repositorioIncidentes);
        modeloHeladera = new ModeloDeHeladera("Modelo X-R98");
        ubicacion = new Ubicacion(-34.5986317f,-58.4212435f,new Calle("Av Medrano", "951"));
        heladera = new Heladera(modeloHeladera,"Medrano",ubicacion);
        sensorMovimiento = new SensorMovimiento();
        //sensorTemperatura = new SensorTemperatura();
        sensorMovimiento.setHeladera(heladera);
        //sensorTemperatura.setHeladera(heladera);
        heladera.setSensorMovimiento(sensorMovimiento);
        //heladera.setSensorTemperatura(sensorTemperatura);
        serviceBroker.agregarSensores(sensorMovimiento);
        //serviceBroker.agregarSensores(sensorTemperatura);
        serviceBroker.init();

    }
}
