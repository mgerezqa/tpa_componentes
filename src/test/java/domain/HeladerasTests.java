package domain;
import domain.geografia.Calle;
import domain.geografia.Ubicacion;
import domain.heladera.Heladera;
import domain.heladera.Sensores.SensorMovimiento;
import domain.heladera.Sensores.SensorTemperatura;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class HeladerasTests {

    private Heladera heladera;
    private String nombre;
    private Ubicacion ubicacion;
    private Integer capacidadMax;
    private LocalDate fechaInicio;
    private Float tMax;
    private Float tMin;
    private SensorTemperatura sensorTemperatura;
    private SensorMovimiento sensorMovimiento;


    @BeforeEach
    public void setUp(){
        nombre = "Heladera Medrano";
        ubicacion = new Ubicacion(-34.5986317f,-58.4212435f,new Calle("Av Medrano", "951"));
        capacidadMax = 35;
        fechaInicio = LocalDate.now();
        tMax = 15f;
        tMin = -12f;
        sensorMovimiento = new SensorMovimiento();
        sensorTemperatura = new SensorTemperatura();

        heladera = new Heladera(ubicacion, nombre, capacidadMax, fechaInicio, tMax, tMin, sensorMovimiento, sensorTemperatura);

    }

    @Test
    public void heladeraDadaDeAlta(){
        Assertions.assertTrue(heladera.estaActivaHeladera());
    }

    @Test
    public void heladeraDadaDeBaja(){
        heladera.cambiarEstadoAFueraDeServicio();
        Assertions.assertFalse(heladera.estaActivaHeladera());
    }

    @Test
    public void heladeraModificada(){
        heladera.setNombreIdentificador("Heladera Campus");
        heladera.setUbicacion(new Ubicacion(-34.6596012f,-58.4705505f,(new Calle("Mozart","2300"))));
        Assertions.assertNotEquals(ubicacion, heladera.getUbicacion());
    }

}
