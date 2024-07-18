package domain;

import domain.contacto.Email;
import domain.contacto.MedioDeContacto;
import domain.contacto.Telefono;
import domain.contacto.Whatsapp;
import domain.formulario.Formulario;
import domain.geografia.*;
import domain.geografia.area.AreaDeCobertura;
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
    private AreaDeCobertura areaDeCobertura;

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
    private SuscripcionPorCantidadDeViandasDisp NotificarCuandoFaltanCincoViandasEnLaHeladera;
    private SuscripcionPorCantidadDeViandasHastaAlcMax NotificarCuandoFalten10ViandasParaAlcanzarelMax;
    private SuscripcionPorDesperfectoH NotificarCuandoSeProduceUnDesperfecto;

    @BeforeEach
    public void setUp() {
        //Medios de contacto
        this.areaDeCobertura = new AreaDeCobertura();
        this.laloEmail = new Email("lalo@gmail.com");
        this.laloTelefono = new Telefono(54,11,400090000);
        this.laloWhatsapp = new Whatsapp("+549116574460");
        this.colaborador = new ColaboradorFisico("Lalo", "Menz",laloEmail);
        this.otroColaborador = new ColaboradorFisico("Pepe","Argento",laloWhatsapp);

        //Geografia

        provinciaSalta = new Provincia("Salta");
        localidadSalta = new Localidad("Centro Salta");
        barrioSalta = new Barrio("Barrio San José");

        provinciaBA = new Provincia("Buenos Aires");
        localidadCABA = new Localidad("CABA");
        barrioAlmagro = new Barrio("Almagro");

        //Heladera
        nombre = "Heladera Medrano";
//        ubicacion = new Ubicacion(-34.5986317f,-58.4212435f,new Calle("Av Medrano", "951"));
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
        NotificarCuandoFaltanCincoViandasEnLaHeladera = (SuscripcionPorCantidadDeViandasDisp) fabrica.crearSuscripcion(eTipoDeSuscripcion.POR_CANTIDAD_DE_VIANDAS_DISP);
        NotificarCuandoFaltanCincoViandasEnLaHeladera.setCantidadDeViandasDisp(5);

        //Genero suscripcion para que informe cuando se esté por alcanzar el max de 10 viandas
        NotificarCuandoFalten10ViandasParaAlcanzarelMax =  (SuscripcionPorCantidadDeViandasHastaAlcMax) fabrica.crearSuscripcion(eTipoDeSuscripcion.POR_CANTIDAD_DE_VIANDAS_HASTA_ALC_MAX);
        NotificarCuandoFalten10ViandasParaAlcanzarelMax.setCantidadDeViandasHastaAlcMax(10);

        //Genero suscripcion para que informe cuando se produce un desperfecto
        NotificarCuandoSeProduceUnDesperfecto = (SuscripcionPorDesperfectoH) fabrica.crearSuscripcion(eTipoDeSuscripcion.POR_DESPERFECTO_H);
    }

    @Test
    @DisplayName("Un Colaborador se suscribe a una heladera según la cantidad de viandas disponibles en ella")
    public void colaboradorSeSuscribeSegunCantidadDeViandasDisponibles() {

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

        /*Evaluar flujo*/
        assertFalse(colaborador.isNotificacionRecibida());
        assertFalse(otroColaborador.isNotificacionRecibida());

        heladeraCABA.cambiarEstadoAFueraDeServicio();

        assertEquals(colaborador.isNotificacionRecibida(),true);
        assertEquals(otroColaborador.isNotificacionRecibida(),true);

    }

    @Test
    @DisplayName("Un colaborador que está frecuenta por la provincia de Salta no puede suscribirse a una heladera que está ubicada en Buenos Aires")

    public void colaboradorNoPuedeSuscribirseAUnaHeladeraQueNoEstaEnSuProvincia(){

        colaborador.getZonaQueFrecuenta().setProvincia(provinciaSalta);
        colaborador.getZonaQueFrecuenta().agregarBarrio(barrioSalta);
        colaborador.getZonaQueFrecuenta().agregarLocalidad(localidadSalta);

        assertThrows(RuntimeException.class, () -> {
            unaSuscripcion = new Suscripcion(heladeraCABA,colaborador, NotificarCuandoFaltanCincoViandasEnLaHeladera);
        });


    }

}
