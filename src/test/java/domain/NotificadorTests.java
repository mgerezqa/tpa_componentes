package domain;

import domain.contacto.Email;
import domain.contacto.MedioDeContacto;
import domain.contacto.Telegram;
import domain.contacto.Whatsapp;
import domain.formulario.documentos.Cuil;
import domain.formulario.documentos.Documento;
import domain.geografia.Calle;
import domain.geografia.Ubicacion;
import domain.heladera.Heladera.Heladera;
import domain.heladera.Heladera.ModeloDeHeladera;
import domain.heladera.Sensores.SensorMovimiento;
import domain.heladera.Sensores.SensorTemperatura;
import domain.incidentes.Alerta;
import domain.incidentes.FallaTecnica;
import domain.incidentes.IncidenteFactory;
import domain.mensajeria.EmailSender;
import domain.mensajeria.TelegramBot;
import domain.suscripciones.Suscripcion;
import domain.suscripciones.SuscripcionPorCantidadDeViandasDisponibles;
import domain.suscripciones.TipoDeSuscripcionFactory;
import domain.suscripciones.eTipoDeSuscripcion;
import domain.usuarios.ColaboradorFisico;
import domain.usuarios.Tecnico;
import domain.usuarios.Usuario;
import dtos.FallaTecnicaDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.notificador.Notificador;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class NotificadorTests {

    private ColaboradorFisico lalo;
    private Tecnico tecnico;
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

    //Alerta
    private Alerta alertaTemperatura;
    //Falla tecnica DTO
    private FallaTecnicaDTO dtoFallaTecnica;
    private FallaTecnica fallaTecnica;

    private Usuario usuario;

    @BeforeEach
    public void setUp() {


        this.tecnico = new Tecnico("Juan", "Perez", mock(Documento.class), mock(Cuil.class));
        //Medios de contacto
        this.laloEmail =  new Email("NOT@gmail.com");
//        this.laloTelegram = new Telegram("melli11ok");
        this.laloTelegram = new Telegram("NOT");
        this.laloWhatsapp = new Whatsapp("+5491161964086");
        this.lalo = new ColaboradorFisico("Lalo", "Menz");

        this.lalo.agregarMedioDeContacto(laloEmail);
        this.lalo.agregarMedioDeContacto(laloTelegram);
        this.lalo.agregarMedioDeContacto(laloWhatsapp);

        this.tecnico.agregarMedioDeContacto(laloEmail);
        this.tecnico.agregarMedioDeContacto(laloTelegram);


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

        //Alerta
        alertaTemperatura = IncidenteFactory.crearAlerta(heladera,"falla_temperatura");
        alertaTemperatura.setId("02");

        //Usuario
        usuario = mock(Usuario.class);

        //FallaTecnica
        dtoFallaTecnica = new FallaTecnicaDTO();
        dtoFallaTecnica.setDescripcion("Falla en el sensor de temperatura");
        dtoFallaTecnica.setNombreUsuario("Homer");
        fallaTecnica = IncidenteFactory.crearFallaTecnica(dtoFallaTecnica,heladera,usuario);
        fallaTecnica.setFechaYHora(LocalDateTime.now());
        fallaTecnica.setId("01");

    }

    @Test
    @DisplayName("Un colaborador puede habilitar Telegram como medio de contacto")
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
    @DisplayName("Un colaborador puede habilitar Email como medio de contacto")
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

    @Test
    @DisplayName("Un tecnico recibe una alerta por mail")
    public void testNotificarTecnicoPorEmail() throws IOException {
        notificador.habilitarNotificacion(tecnico, laloEmail);
        notificador.notificar(tecnico,alertaTemperatura);
    }

    @Test
    @DisplayName("Un tecnico recibe un aviso de falla técnica por mail")
    public void testNotificarTecnicoPorFallaPorEmail() throws IOException {
        notificador.habilitarNotificacion(tecnico, laloEmail);
        notificador.notificar(tecnico,fallaTecnica);
    }

    @Test
    @DisplayName("Un colaborador puede habilitar Telegram como medio de contacto")
    public void testHabilitarTelegramParaTecnico() {
        notificador.habilitarNotificacion(tecnico, laloTelegram);
        assertTrue(lalo.getMediosDeContacto().stream()
                .filter(medio -> medio.tipoMedioDeContacto().equals("Telegram"))
                .anyMatch(MedioDeContacto::isNotificar), "Telegram debería estar habilitado para notificaciones");

        notificador.deshabilitarNotificacion(tecnico, laloTelegram);
        assertFalse(lalo.getMediosDeContacto().stream()
                .filter(medio -> medio.tipoMedioDeContacto().equals("Telegram"))
                .anyMatch(MedioDeContacto::isNotificar), "Telegram no debería estar habilitado para notificaciones");
    }

    @Test
    @DisplayName("Un colaborador puede habilitar Email como medio de contacto")
    public void testHabilitarCorreoParaTecnico() {
        notificador.habilitarNotificacion(tecnico, laloEmail);
        assertTrue(lalo.getMediosDeContacto().stream()
                .filter(medio -> medio.tipoMedioDeContacto().equals("Email"))
                .anyMatch(MedioDeContacto::isNotificar), "Email debería estar habilitado para notificaciones");

        notificador.deshabilitarNotificacion(tecnico, laloEmail);
        assertFalse(lalo.getMediosDeContacto().stream()
                .filter(medio -> medio.tipoMedioDeContacto().equals("Email"))
                .anyMatch(MedioDeContacto::isNotificar), "Email no debería estar habilitado para notificaciones");
    }

    @Test
    @DisplayName("Un tecnico recibe un aviso de falla técnica por Telegram")
    public void testNotificarTecnicoPorFallaPorTelegram() throws IOException {
        notificador.habilitarNotificacion(tecnico, laloTelegram);
        notificador.notificar(tecnico,fallaTecnica);
    }




}
