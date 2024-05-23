package domain;
import domain.geografia.Calle;
import domain.geografia.Ubicacion;
import domain.heladera.Heladera;
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

    @BeforeEach
    public void setUp(){
        nombre = "Heladera Medrano";
        ubicacion = new Ubicacion(-34.5986317f,-58.4212435f,new Calle("Av Medrano", "951"));
        capacidadMax = 35;
        fechaInicio = LocalDate.now();
        tMax = 15f;
        tMin = -12f;

        heladera = new Heladera(nombre, ubicacion, capacidadMax, fechaInicio, tMax, tMin);

    }

    @Test
    public void heladeraDadaDeAlta(){
        Assertions.assertTrue(heladera.heladeraEstaActiva());
    }

    @Test
    public void heladeraDadaDeBaja(){
        heladera.darDeBajaHeladera();
        Assertions.assertFalse(heladera.heladeraEstaActiva());
    }

    @Test
    public void heladeraModificada(){
        heladera.setNombreIdentificador("Heladera Campus");
        heladera.setUbicacion(new Ubicacion(-34.6596012f,-58.4705505f,(new Calle("Mozart","2300"))));
        Assertions.assertNotEquals(ubicacion,heladera.getUbicacion());
    }

}
