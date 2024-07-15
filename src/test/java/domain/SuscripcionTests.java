package domain;

import domain.contacto.Email;
import domain.contacto.MedioDeContacto;
import domain.contacto.Telefono;
import domain.contacto.Whatsapp;
import domain.formulario.Formulario;
import domain.geografia.Calle;
import domain.geografia.Ubicacion;
import domain.heladera.Heladera.Heladera;
import domain.heladera.Heladera.ModeloDeHeladera;
import domain.heladera.Sensores.SensorMovimiento;
import domain.heladera.Sensores.SensorTemperatura;
import domain.suscripciones.*;
import domain.usuarios.ColaboradorFisico;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SuscripcionTests {
    /*Colaborador*/
    private ColaboradorFisico colaborador;
    private MedioDeContacto laloEmail;
    private MedioDeContacto laloTelefono;
    private MedioDeContacto laloWhatsapp;

    /*Heladeras*/
    private Formulario formulario;
    private Heladera heladera;
    private String nombre;
    private Ubicacion ubicacion;
    private Integer capacidadMax;
    private LocalDate fechaInicio;
    private SensorTemperatura sensorTemperatura;
    private SensorMovimiento sensorMovimiento;
    private ModeloDeHeladera modeloHeladera;
    private Float tempMin;
    private Float tempMax;
    private List<String> historialDeEstados;
    private Suscripcion suscripcion;
    private SuscripcionFactory suscripcionFactory;


    @BeforeEach
    public void setUp() {
        //Medios de contacto
        this.laloEmail = new Email("lalo@gmail.com");
        this.laloTelefono = new Telefono(54,11,400090000);
        this.laloWhatsapp = new Whatsapp("+549116574460");
        this.colaborador = new ColaboradorFisico("Lalo", "Menz",laloEmail);
        this.formulario = new Formulario();

        //Heladera
        nombre = "Heladera Medrano";
        ubicacion = new Ubicacion(-34.5986317f,-58.4212435f,new Calle("Av Medrano", "951"));
        capacidadMax = 35;
        fechaInicio = LocalDate.now();
        modeloHeladera = new ModeloDeHeladera("Modelo X-R98");
        tempMin = modeloHeladera.getTemperaturaMinima();
        tempMax = modeloHeladera.getTemperaturaMaxima();
        heladera = new Heladera(modeloHeladera,nombre,ubicacion);
        sensorMovimiento = new SensorMovimiento(heladera);
        sensorTemperatura = new SensorTemperatura(heladera);
        TipoDeSuscripcion tipoDeSuscripcion = suscripcionFactory.crearSuscripcion(eTipoDeSuscripcion.POR_CANTIDAD_DE_VIANDAS_DISP);


    }

    @Test
    public void testSuscripcionPorCantidadDeViandasDisp() {
        /*Genero suscripcion para que me informe cuando restan 5 viandas*/
        tipoDeSuscripcion = (SuscripcionPorCantidadDeViandasDisp) tipoDeSuscripcion.setCantidadDeViandasDisp(5);
        suscripcion = new Suscripcion(heladera, colaborador, tipoDeSuscripcion);
        /*Evaluar flujo*/
        heladera.setCapacidadActual(6);
        assertFalse(colaborador.isNotificacionRecibida());
        heladera.setCapacidadActual(5);
        assertTrue(colaborador.isNotificacionRecibida());


    }

    @Test
    public void colaboradorSeSuscribeSegunCantidadDeViandasDisponibles(){


        //Generar suscripción


    }

}
