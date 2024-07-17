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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SuscripcionTests {
    /*Colaborador*/
    private ColaboradorFisico colaborador;
    private ColaboradorFisico otroColaborador;
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

    /*Tipo de Suscripciones*/
    private TipoDeSuscripcionFactory fabrica;

    /*Suscripcion */
    private Suscripcion unaSuscripcion;
    private Suscripcion otraSuscripcion;
    private SuscripcionPorCantidadDeViandasDisp NotificarCuandoFaltanCincoViandasEnLaHeladera;
    private SuscripcionPorCantidadDeViandasHastaAlcMax NotificarCuandoFalten10ViandasParaAlcanzarelMax;

    @BeforeEach
    public void setUp() {
        //Medios de contacto
        this.laloEmail = new Email("lalo@gmail.com");
        this.laloTelefono = new Telefono(54,11,400090000);
        this.laloWhatsapp = new Whatsapp("+549116574460");
        this.colaborador = new ColaboradorFisico("Lalo", "Menz",laloEmail);
        this.otroColaborador = new ColaboradorFisico("Pepe","Argento",laloWhatsapp);

        //Heladera
        nombre = "Heladera Medrano";
        ubicacion = new Ubicacion(-34.5986317f,-58.4212435f,new Calle("Av Medrano", "951"));
        capacidadMax = 35;
        fechaInicio = LocalDate.now();
        modeloHeladera = new ModeloDeHeladera("Modelo X-R98");
        tempMin = modeloHeladera.getTemperaturaMinima();
        tempMax = modeloHeladera.getTemperaturaMaxima();
        sensorMovimiento = new SensorMovimiento(heladera);
        sensorTemperatura = new SensorTemperatura(heladera);
        //instancia de heladera
        heladera = new Heladera(modeloHeladera,nombre,ubicacion);


        //Tipo de suscripciones
        fabrica = new TipoDeSuscripcionFactory();

        //Genero suscripcion para que me informe cuando restan 5 viandas
        NotificarCuandoFaltanCincoViandasEnLaHeladera = (SuscripcionPorCantidadDeViandasDisp) fabrica.crearSuscripcion(eTipoDeSuscripcion.POR_CANTIDAD_DE_VIANDAS_DISP);
        NotificarCuandoFaltanCincoViandasEnLaHeladera.setCantidadDeViandasDisp(5);

        NotificarCuandoFalten10ViandasParaAlcanzarelMax =  (SuscripcionPorCantidadDeViandasHastaAlcMax) fabrica.crearSuscripcion(eTipoDeSuscripcion.POR_CANTIDAD_DE_VIANDAS_HASTA_ALC_MAX);
        NotificarCuandoFalten10ViandasParaAlcanzarelMax.setCantidadDeViandasHastaAlcMax(10);
    }

    @Test
    @DisplayName("Colaborador se suscribe a una heladera según cantidad de viandas disponibles")
    public void colaboradorSeSuscribeSegunCantidadDeViandasDisponibles() {

        //Suscripcion
        unaSuscripcion = new Suscripcion(heladera,colaborador, NotificarCuandoFaltanCincoViandasEnLaHeladera);

        /*Evaluar flujo*/
        heladera.setCapacidadActual(6);
        assertFalse(colaborador.isNotificacionRecibida());

        heladera.retirarVianda(); //Capacidad actual 5 | recibe notificación
        assertEquals(colaborador.isNotificacionRecibida(),true);

    }

    @Test
    @DisplayName("Colaborador se suscribe a una heladera según cantidad de viandas necesarias para alcanzar el máximo")
    public void colaboradorSeSuscribeSegunCantidadDeViandasNecesariasParaAlcanzarElMaximo(){
        //Suscripcion
        //La suscripción informa al colaborador cuando falten 10 viandas hasta alcanzar el máximo
        unaSuscripcion = new Suscripcion(heladera,colaborador, NotificarCuandoFalten10ViandasParaAlcanzarelMax);

        /*Evaluar flujo*/

        heladera.setCapacidadActual(13);
        heladera.setCapacidadMax(25);

        assertFalse(colaborador.isNotificacionRecibida()); //capacidad actual 13 | hasta alcanzar max 12
        heladera.ingresarVianda(); //capacidad actual 14 | hasta alcanzar max 11
        heladera.ingresarVianda(); //capacidad actual 15 | hasta alcanzar max 10
        heladera.ingresarVianda(); //capacidad actual 16 | recibe nueva notificación
        assertEquals(colaborador.isNotificacionRecibida(),true);


    }


    @Test
    @DisplayName("Colaborador se suscribe a una heladera según cantidad de viandas necesarias para alcanzar el máximo, la heladera ya alcanzó el máximo establecido en la suscripción")
    public void colaboradorSeSuscribeSegunCantidadDeViandasNecesariasParaSuperarElMaximo(){
        //Suscripcion
        //La suscripción informa al colaborador cuando falten 10 viandas hasta alcanzar el máximo
        unaSuscripcion = new Suscripcion(heladera,colaborador, NotificarCuandoFalten10ViandasParaAlcanzarelMax);

        /*Evaluar flujo*/

        heladera.setCapacidadActual(17);
        heladera.setCapacidadMax(25);

        heladera.ingresarVianda(); //capacidad actual 17 | superó el máximo (-8), el eventManager notifica despues de ingresar la vianda

        assertEquals(colaborador.isNotificacionRecibida(),true);


    }

    @Test
    @DisplayName("Dos colaboradores se suscriben a la misma heladera bajo el mismo criterio")

    public void dosColaboradoresSeSuscribenAlaMismaHeladeraBajoMismoCriterio(){

        //Suscripcion
        unaSuscripcion = new Suscripcion(heladera,colaborador, NotificarCuandoFaltanCincoViandasEnLaHeladera);
        otraSuscripcion = new Suscripcion(heladera,otroColaborador, NotificarCuandoFaltanCincoViandasEnLaHeladera);
        /*Evaluar flujo*/

        heladera.setCapacidadActual(6);
        assertFalse(colaborador.isNotificacionRecibida());
        assertFalse(otroColaborador.isNotificacionRecibida());

        heladera.retirarVianda();
        assertEquals(colaborador.isNotificacionRecibida(),true);
        assertEquals(otroColaborador.isNotificacionRecibida(),true);

    }

    @Test
    @DisplayName("Dos colaboradores se suscriben a la misma heladera bajo criterios diferentes")

    public void dosColaboradoresSeSuscribenAlaMismaHeladeraBajoCriteriosDiferentes(){

        heladera.setCapacidadActual(6);
        heladera.setCapacidadMax(25);

        //Suscripcion
        unaSuscripcion = new Suscripcion(heladera,colaborador, NotificarCuandoFaltanCincoViandasEnLaHeladera);
        otraSuscripcion = new Suscripcion(heladera,otroColaborador, NotificarCuandoFalten10ViandasParaAlcanzarelMax);

        /*Evaluar flujo*/
        assertFalse(colaborador.isNotificacionRecibida());
        assertFalse(otroColaborador.isNotificacionRecibida());

        heladera.retirarVianda(); //Capacidad actual 5 | recibe notificación

        assertEquals(colaborador.isNotificacionRecibida(),true);

        for(int i =0 ; i<9; i++){
        heladera.ingresarVianda();
        }

        heladera.ingresarVianda(); //Capacidad actual 15 | recibe notificación
        assertEquals(otroColaborador.isNotificacionRecibida(),true);

    }

}
