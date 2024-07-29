package domain;

import domain.contacto.Email;
import domain.contacto.MedioDeContacto;
import domain.contacto.Telegram;
import domain.contacto.Whatsapp;
import domain.geografia.Calle;
import domain.geografia.Ubicacion;
import domain.heladera.Heladera.Heladera;
import domain.heladera.Heladera.ModeloDeHeladera;
import domain.heladera.Sensores.SensorMovimiento;
import domain.heladera.Sensores.SensorTemperatura;
import domain.mensajeria.EmailSender;
import domain.mensajeria.TelegramBot;
import domain.suscripciones.Suscripcion;
import domain.suscripciones.SuscripcionPorCantidadDeViandasDisponibles;
import domain.suscripciones.TipoDeSuscripcionFactory;
import domain.suscripciones.eTipoDeSuscripcion;
import domain.usuarios.ColaboradorFisico;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.notificador.Notificador;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class NotificadorTests {

    private ColaboradorFisico lalo;
    private MedioDeContacto laloEmail;
    private MedioDeContacto laloTelegram;
    private MedioDeContacto laloWhatsapp;

    //Heladera
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

    //Notificador
    private Notificador notificador;
    //Config
    private Config config;
    /*Senders */
    private TelegramBot telegramBot;
    private EmailSender emailSender;

    //Suscripciones
    private TipoDeSuscripcionFactory fabrica;
    private Suscripcion unaSuscripcion;
    private SuscripcionPorCantidadDeViandasDisponibles NotificarCuandoFaltanCincoViandasEnLaHeladera;



    @BeforeEach
    public void setUp() {

        //Medios de contacto
        this.laloEmail = new Email("mingerez@gmail.com");
        this.laloTelegram = new Telegram("+549116574460");
        this.laloWhatsapp = new Whatsapp("+549116574460");
        this.lalo = new ColaboradorFisico("Lalo", "Menz");

        this.lalo.agregarMedioDeContacto(laloEmail);
        this.lalo.agregarMedioDeContacto(laloTelegram);
        this.lalo.agregarMedioDeContacto(laloWhatsapp);


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

        //Notificador
        notificador = new Notificador();
        //Telegram Bot
        telegramBot = TelegramBot.getInstance();
        //Email Sender
        emailSender = EmailSender.getInstance();
        //Config
        config = Config.getInstance();

        //Tipo de suscripciones
        fabrica = new TipoDeSuscripcionFactory();
        //Genero suscripcion para que  informe cuando restan 5 viandas
        NotificarCuandoFaltanCincoViandasEnLaHeladera = (SuscripcionPorCantidadDeViandasDisponibles) fabrica.crearSuscripcion(eTipoDeSuscripcion.POR_CANTIDAD_DE_VIANDAS_DISP);
        NotificarCuandoFaltanCincoViandasEnLaHeladera.setCantidadDeViandasDisp(5);


    }

    @Test
    @DisplayName("Se puede habilitar Telegram como medio de contacto")
    public void testHabilitarTelegram() {
        notificador.habilitarNotificacion(lalo, laloTelegram);
        assertTrue(lalo.getMediosDeContacto().stream()
                .filter(medio -> medio.tipoMedioDeContacto().equals("Telegram"))
                .anyMatch(MedioDeContacto::isNotificar), "Telegram debería estar habilitado para notificaciones");

        notificador.deshabilitarNotificacion(lalo, laloTelegram);
        assertFalse(lalo.getMediosDeContacto().stream()
                .filter(medio -> medio.tipoMedioDeContacto().equals("Telegram"))
                .anyMatch(MedioDeContacto::isNotificar), "Telegram no debería estar habilitado para notificaciones");
    }

    @Test
    @DisplayName("Se puede habilitar Email como medio de contacto")
    public void testHabilitarCorreo() {
        notificador.habilitarNotificacion(lalo, laloEmail);
        assertTrue(lalo.getMediosDeContacto().stream()
                .filter(medio -> medio.tipoMedioDeContacto().equals("Email"))
                .anyMatch(MedioDeContacto::isNotificar), "Email debería estar habilitado para notificaciones");

        notificador.deshabilitarNotificacion(lalo, laloEmail);
        assertFalse(lalo.getMediosDeContacto().stream()
                .filter(medio -> medio.tipoMedioDeContacto().equals("Email"))
                .anyMatch(MedioDeContacto::isNotificar), "Email no debería estar habilitado para notificaciones");
    }

    @Test
    @DisplayName("Un colaborador recibe una notificación de tipo suscripcion por telegram ")
    public void testNotificarPorTelegram() throws IOException {
        notificador.habilitarNotificacion(lalo, laloTelegram);
        notificador.notificar(lalo,heladera, NotificarCuandoFaltanCincoViandasEnLaHeladera);

    }

    @Test
    @DisplayName("Un colaborador recibe una notificación de tipo suscripcion por email ")
    public void testNotificarPorEmail() throws IOException {
        notificador.habilitarNotificacion(lalo, laloEmail);
        notificador.notificar(lalo,heladera, NotificarCuandoFaltanCincoViandasEnLaHeladera);
    }
}
