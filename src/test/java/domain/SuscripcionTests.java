package domain;

import domain.contacto.Email;
import domain.contacto.MedioDeContacto;
import domain.contacto.Telegram;
import domain.contacto.Whatsapp;
import domain.formulario.Formulario;
import domain.geografia.*;
import domain.geografia.area.AreaDeCobertura;
import domain.heladera.Heladera.Heladera;
import domain.heladera.Heladera.ModeloDeHeladera;
import domain.heladera.Sensores.SensorMovimiento;
import domain.heladera.Sensores.SensorTemperatura;
import domain.mensajeria.EmailSender;
import domain.mensajeria.TelegramBot;
import domain.suscripciones.*;
import domain.usuarios.ColaboradorFisico;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.notificador.Notificador;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SuscripcionTests {
    /*Config file*/
    private Config config;
    /*Colaborador*/
    private ColaboradorFisico colaborador;
    private ColaboradorFisico otroColaborador;
    private MedioDeContacto laloEmail;
    private MedioDeContacto laloTelegram;
    private MedioDeContacto laloWhatsapp;
    private AreaDeCobertura areaDeCobertura;
    private AreaDeCobertura otraAreaDeCobertura;

    /*Heladeras*/
    private Formulario formulario;
    private Heladera heladeraCABA;
    private Heladera heladeraSalta;
    private String nombre;
    private Ubicacion ubicacionCABA;
    private Ubicacion ubicacionSalta;
    private Integer capacidadMax;
    private LocalDate fechaInicio;
    private SensorTemperatura sensorTemperatura;
    private SensorMovimiento sensorMovimiento;
    private ModeloDeHeladera modeloHeladera;
    private Float tempMin;
    private Float tempMax;
    private List<String> historialDeEstados;
    private Provincia provinciaSalta;
    private Provincia provinciaBA;
    private Barrio barrioAlmagro;
    private Localidad localidadCABA;
    private Localidad localidadSalta;
    private Barrio barrioSalta;

    /*Tipo de Suscripciones*/
    private TipoDeSuscripcionFactory fabrica;

    /*Suscripcion */
    private Suscripcion unaSuscripcion;
    private Suscripcion otraSuscripcion;
    private SuscripcionPorCantidadDeViandasDisponibles NotificarCuandoFaltanCincoViandasEnLaHeladera;
    private SuscripcionPorCantidadDeViandasHastaAlcMax NotificarCuandoFalten10ViandasParaAlcanzarelMax;
    private SuscripcionPorDesperfectoH NotificarCuandoSeProduceUnDesperfecto;

    /*Notificador*/
    private Notificador notificador;

    /*Senders */
    private TelegramBot telegramBot;
    private EmailSender emailSender;


    @BeforeEach
    public void setUp() {
        //Config
        config = Config.getInstance();

        //Medios de contacto
        this.laloEmail = new Email("bbattagliese@frba.utn.edu.ar");
        this.laloTelegram = new Telegram("NOfdsfafsT");
        this.laloWhatsapp = new Whatsapp("+5491161964086");
        this.colaborador = new ColaboradorFisico("Lalo", "Menz");
        this.otroColaborador = new ColaboradorFisico("Pepe","Argento");
        this.areaDeCobertura = new AreaDeCobertura(colaborador);
        this.otraAreaDeCobertura = new AreaDeCobertura(otroColaborador);

        //Geografia

        provinciaSalta = new Provincia("Salta");
        localidadSalta = new Localidad("Centro Salta");
        barrioSalta = new Barrio("Barrio San José");

        provinciaBA = new Provincia("Buenos Aires");
        localidadCABA = new Localidad("CABA");
        barrioAlmagro = new Barrio("Almagro");

        //Area de cobertura
        areaDeCobertura.agregarProvincia(provinciaBA);
        areaDeCobertura.agregarLocalidad(localidadCABA);
        areaDeCobertura.agregarBarrio(barrioAlmagro);

        otraAreaDeCobertura.agregarProvincia(provinciaBA);
        otraAreaDeCobertura.agregarLocalidad(localidadCABA);
        otraAreaDeCobertura.agregarBarrio(barrioAlmagro);
        //Heladera
        nombre = "Heladera Medrano";
        ubicacionCABA = new Ubicacion(provinciaBA, localidadCABA, barrioAlmagro);
        ubicacionSalta = new Ubicacion(provinciaSalta,localidadSalta,barrioSalta);

        capacidadMax = 35;
        fechaInicio = LocalDate.now();
        modeloHeladera = new ModeloDeHeladera("Modelo X-R98");
        tempMin = modeloHeladera.getTemperaturaMinima();
        tempMax = modeloHeladera.getTemperaturaMaxima();
        sensorMovimiento = new SensorMovimiento(heladeraCABA);
        sensorTemperatura = new SensorTemperatura(heladeraCABA);
        //instancia de heladera
        heladeraCABA = new Heladera(modeloHeladera,nombre, ubicacionCABA);
        heladeraSalta = new Heladera(modeloHeladera,nombre, ubicacionSalta);


        //Tipo de suscripciones
        fabrica = new TipoDeSuscripcionFactory();

        //Genero suscripcion para que  informe cuando restan 5 viandas
        NotificarCuandoFaltanCincoViandasEnLaHeladera = (SuscripcionPorCantidadDeViandasDisponibles) fabrica.crearSuscripcion(eTipoDeSuscripcion.POR_CANTIDAD_DE_VIANDAS_DISP);
        NotificarCuandoFaltanCincoViandasEnLaHeladera.setCantidadDeViandasDisp(5);

        //Genero suscripcion para que informe cuando se esté por alcanzar el max de 10 viandas
        NotificarCuandoFalten10ViandasParaAlcanzarelMax =  (SuscripcionPorCantidadDeViandasHastaAlcMax) fabrica.crearSuscripcion(eTipoDeSuscripcion.POR_CANTIDAD_DE_VIANDAS_HASTA_ALC_MAX);
        NotificarCuandoFalten10ViandasParaAlcanzarelMax.setCantidadDeViandasHastaAlcMax(10);

        //Genero suscripcion para que informe cuando se produce un desperfecto
        NotificarCuandoSeProduceUnDesperfecto = (SuscripcionPorDesperfectoH) fabrica.crearSuscripcion(eTipoDeSuscripcion.POR_DESPERFECTO_H);

        //Agregar medios de contacto
        colaborador.agregarMedioDeContacto(laloEmail);
        colaborador.agregarMedioDeContacto(laloTelegram);
        otroColaborador.agregarMedioDeContacto(laloEmail);
        otroColaborador.agregarMedioDeContacto(laloTelegram);
        //Notificador
        notificador = new Notificador();

        //Habilitar Notificaciones
//        notificador.habilitarNotificacion(colaborador, laloTelegram);
//        notificador.habilitarNotificacion(otroColaborador, laloTelegram);
        notificador.habilitarNotificacion(colaborador, laloEmail);
//        notificador.habilitarNotificacion(otroColaborador, laloEmail);
        //Telegram Bot

        telegramBot = TelegramBot.getInstance();
        //Email Sender
        emailSender = EmailSender.getInstance();

    }

    @Test
    @DisplayName("Un Colaborador se suscribe a una heladera según la cantidad de viandas disponibles en ella")
    public void colaboradorSeSuscribeSegunCantidadDeViandasDisponibles() {

        //Area de cobertura
        AreaDeCobertura zonaQueFrecuenta = new AreaDeCobertura(colaborador);
        zonaQueFrecuenta.agregarProvincia(provinciaBA);
        zonaQueFrecuenta.agregarLocalidad(localidadCABA);
        zonaQueFrecuenta.agregarBarrio(barrioAlmagro);

        //Suscripcion
        unaSuscripcion = new Suscripcion(heladeraCABA,colaborador, NotificarCuandoFaltanCincoViandasEnLaHeladera);

        /*Evaluar flujo*/
        heladeraCABA.setCapacidadActual(6);
        assertFalse(colaborador.isNotificacionRecibida());

        heladeraCABA.retirarVianda(); //Capacidad actual 5 | recibe notificación
        assertEquals(colaborador.isNotificacionRecibida(),true);

    }

    @Test
    @DisplayName("Un Colaborador se suscribe a una heladera según la cantidad de viandas necesarias para que alcance su máximo de capacidad")
    public void colaboradorSeSuscribeSegunCantidadDeViandasNecesariasParaAlcanzarElMaximo(){
        //Suscripcion
        //La suscripción informa al colaborador cuando falten 10 viandas hasta alcanzar el máximo
        unaSuscripcion = new Suscripcion(heladeraCABA,colaborador, NotificarCuandoFalten10ViandasParaAlcanzarelMax);

        /*Evaluar flujo*/

        heladeraCABA.setCapacidadActual(13);
        heladeraCABA.setCapacidadMax(25);

        assertFalse(colaborador.isNotificacionRecibida()); //capacidad actual 13 | hasta alcanzar max 12
        heladeraCABA.ingresarVianda(); //capacidad actual 14 | hasta alcanzar max 11
        heladeraCABA.ingresarVianda(); //capacidad actual 15 | hasta alcanzar max 10
        heladeraCABA.ingresarVianda(); //capacidad actual 16 | recibe nueva notificación
        assertEquals(colaborador.isNotificacionRecibida(),true);


    }


    @Test
    @DisplayName("Un colaborador se suscribe a una heladera según la cantidad de viandas necesarias para que alcance su máximo, si la heladera ya alcanzó el máximo establecido en la suscripción cont notificando")
    public void colaboradorSeSuscribeSegunCantidadDeViandasNecesariasParaSuperarElMaximo(){
        //Suscripcion
        //La suscripción informa al colaborador cuando falten 10 viandas hasta alcanzar el máximo
        unaSuscripcion = new Suscripcion(heladeraCABA,colaborador, NotificarCuandoFalten10ViandasParaAlcanzarelMax);

        /*Evaluar flujo*/

        heladeraCABA.setCapacidadActual(17);
        heladeraCABA.setCapacidadMax(25);

        heladeraCABA.ingresarVianda(); //capacidad actual 17 | superó el máximo (-8), el eventManager notifica despues de ingresar la vianda

        assertEquals(colaborador.isNotificacionRecibida(),true);


    }

    @Test
    @DisplayName("Un colaborador suscrito según maximo de viandas, no es notificado si la heladera no alcanza el limite establecido")
    public void colaboradorQueSeSuscribeYNoSeCumpleCriterioNoESNotificado(){
        //Suscripcion
        //La suscripción informa al colaborador cuando falten 10 viandas hasta alcanzar el máximo
        unaSuscripcion = new Suscripcion(heladeraCABA,colaborador, NotificarCuandoFalten10ViandasParaAlcanzarelMax);

        /*Evaluar flujo*/

        heladeraCABA.setCapacidadActual(5);
        heladeraCABA.setCapacidadMax(25);

        heladeraCABA.ingresarVianda(); //capacidad actual 19 , no se genera notifiación

        assertEquals(colaborador.isNotificacionRecibida(),false);


    }

    @Test
    @DisplayName("Dos colaboradores se suscriben a la misma heladera bajo el mismo criterio")

    public void dosColaboradoresSeSuscribenAlaMismaHeladeraBajoMismoCriterio(){

        //Suscripcion
        unaSuscripcion = new Suscripcion(heladeraCABA,colaborador, NotificarCuandoFaltanCincoViandasEnLaHeladera);
        otraSuscripcion = new Suscripcion(heladeraCABA,otroColaborador, NotificarCuandoFaltanCincoViandasEnLaHeladera);
        /*Evaluar flujo*/

        heladeraCABA.setCapacidadActual(6);
        assertFalse(colaborador.isNotificacionRecibida());
        assertFalse(otroColaborador.isNotificacionRecibida());

        heladeraCABA.retirarVianda();
        assertEquals(colaborador.isNotificacionRecibida(),true);
        assertEquals(otroColaborador.isNotificacionRecibida(),true);

    }

    @Test
    @DisplayName("Dos colaboradores se suscriben a la misma heladera bajo criterios diferentes")

    public void dosColaboradoresSeSuscribenAlaMismaHeladeraBajoCriteriosDiferentes(){

        heladeraCABA.setCapacidadActual(6);
        heladeraCABA.setCapacidadMax(25);

        //Suscripcion
        unaSuscripcion = new Suscripcion(heladeraCABA,colaborador, NotificarCuandoFaltanCincoViandasEnLaHeladera);
        otraSuscripcion = new Suscripcion(heladeraCABA,otroColaborador, NotificarCuandoFalten10ViandasParaAlcanzarelMax);

        /*Evaluar flujo*/
        assertFalse(colaborador.isNotificacionRecibida());
        assertFalse(otroColaborador.isNotificacionRecibida());

        heladeraCABA.retirarVianda(); //Capacidad actual 5 | recibe notificación

        assertEquals(colaborador.isNotificacionRecibida(),true);

        for(int i =0 ; i<9; i++){
        heladeraCABA.ingresarVianda();
        }

        heladeraCABA.ingresarVianda(); //Capacidad actual 15 | recibe notificación
        assertEquals(otroColaborador.isNotificacionRecibida(),true);

    }

    @Test
    @DisplayName("Un colaborador se suscribe a una heladera cuando se produce un desperfecto")
    public void unColabodorSuscriptoAUnaHeladeraConDesperfecto(){

        //Suscripcion
        unaSuscripcion = new Suscripcion(heladeraCABA,colaborador, NotificarCuandoSeProduceUnDesperfecto);

        /*Evaluar flujo*/
        heladeraCABA.cambiarEstadoAFueraDeServicio();
        assertTrue(colaborador.isNotificacionRecibida());
    }

    @Test
    @DisplayName("Dos colaboradores se suscriben a la misma heladera por suscripción por desperfecto")

    public void dosColaboradoresSeSuscribenAlaMismaHeladeraPorDesperfectos(){


        //Suscripcion
        unaSuscripcion = new Suscripcion(heladeraCABA,colaborador, NotificarCuandoSeProduceUnDesperfecto);
        otraSuscripcion = new Suscripcion(heladeraCABA,otroColaborador, NotificarCuandoSeProduceUnDesperfecto);

        colaborador.agregarMedioDeContacto(laloTelegram);
        notificador.habilitarNotificacion(colaborador, laloTelegram);

        /*Evaluar flujo*/
        assertFalse(colaborador.isNotificacionRecibida());
        assertFalse(otroColaborador.isNotificacionRecibida());

        heladeraCABA.cambiarEstadoAFueraDeServicio();

        assertEquals(colaborador.isNotificacionRecibida(),true);
        assertEquals(otroColaborador.isNotificacionRecibida(),true);

    }

    @Test
    @DisplayName("Un colaborador que frecuenta por la provincia de Salta NO PUEDE SUSCRIBIRSE a una heladera que está ubicada en Buenos Aires")

    public void colaboradorNoPuedeSuscribirseAUnaHeladeraQueNoEstaEnSuProvincia(){


        AreaDeCobertura zonaQueFrecuenta = new AreaDeCobertura(colaborador);
        zonaQueFrecuenta.agregarProvincia(provinciaSalta);
        zonaQueFrecuenta.agregarBarrio(barrioSalta);
        zonaQueFrecuenta.agregarLocalidad(localidadSalta);


        assertThrows(RuntimeException.class, () -> {
            unaSuscripcion = new Suscripcion(heladeraCABA,colaborador, NotificarCuandoFaltanCincoViandasEnLaHeladera);
        });


    }

    @Test
    @DisplayName("Un colaborador que frecuenta por la provincia de Salta y localidad puede suscribirse a una heladera ubicada en la misma provincia y localidad pero diferente barrio")

    public void colaboradorPuedeSuscribirseAunaHeladeraSiFrecuentaPorEsaProvinciaYLocalidadPeroBarriosDiferentes(){

        AreaDeCobertura zonaQueFrecuenta = new AreaDeCobertura(colaborador);
        zonaQueFrecuenta.agregarProvincia(provinciaSalta);
        zonaQueFrecuenta.agregarLocalidad(localidadSalta);

        Barrio barrioSanMartin = new Barrio("Barrio San Martín");
        Barrio barrioSanPedro = new Barrio("Barrio San Pedro");

        zonaQueFrecuenta.agregarBarrio(barrioSanMartin);
        zonaQueFrecuenta.agregarBarrio(barrioSanPedro);


        unaSuscripcion = new Suscripcion(heladeraSalta,colaborador, NotificarCuandoFaltanCincoViandasEnLaHeladera);

    }



    @Test
    @DisplayName("Un colaborador que frecuenta por la provincia de Salta,centro Salta,barrios San Jose, San Martin y San Pedro, se puede suscribir a una heladera que está ubicada en San Pedro")

    public void colaboradorPuedeSuscribirseAunaHeladeraSiFrecuentaPorEsaProvinciaYLocalidadYAlgunosBarriosCoincidentes(){

        AreaDeCobertura zonaQueFrecuenta = new AreaDeCobertura(colaborador);
        zonaQueFrecuenta.agregarProvincia(provinciaSalta);
        zonaQueFrecuenta.agregarLocalidad(localidadSalta);

        Barrio barrioSanMartin = new Barrio("Barrio San Martín");
        Barrio barrioSanPedro = new Barrio("Barrio San Pedro");

        zonaQueFrecuenta.agregarBarrio(barrioSanMartin);
        zonaQueFrecuenta.agregarBarrio(barrioSanPedro);
        zonaQueFrecuenta.agregarBarrio(barrioSalta);


        unaSuscripcion = new Suscripcion(heladeraSalta,colaborador, NotificarCuandoFaltanCincoViandasEnLaHeladera);

    }

}
