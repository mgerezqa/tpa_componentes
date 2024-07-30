package domain;
import domain.contacto.Email;
import domain.geografia.Calle;
import domain.geografia.Ubicacion;
import domain.heladera.Heladera.EstadoHeladera;
import domain.heladera.Heladera.Heladera;
import domain.heladera.Heladera.ModeloDeHeladera;
import domain.heladera.Heladera.SolicitudApertura;
import domain.heladera.Sensores.SensorMovimiento;
import domain.heladera.Sensores.SensorTemperatura;
import domain.usuarios.ColaboradorFisico;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class HeladerasTests {

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
    private ColaboradorFisico lalo;

    @BeforeEach
    public void setUp(){
        nombre = "Heladera Medrano";
        ubicacion = new Ubicacion(-34.5986317f,-58.4212435f,new Calle("Av Medrano", "951"));
        capacidadMax = 35;
        fechaInicio = LocalDate.now();
        sensorMovimiento = new SensorMovimiento(heladera);
        sensorTemperatura = new SensorTemperatura(heladera);

        modeloHeladera = new ModeloDeHeladera("Modelo X-R98");
        tempMin = modeloHeladera.getTemperaturaMinima();
        tempMax = modeloHeladera.getTemperaturaMaxima();

        heladera = new Heladera(modeloHeladera,nombre,ubicacion);
        heladera = new Heladera(modeloHeladera,nombre,ubicacion);
        sensorMovimiento = new SensorMovimiento(heladera);
        sensorTemperatura = new SensorTemperatura(heladera);

        lalo = new ColaboradorFisico("Lalo", "Menz", new Email("lalo@gmail.com"));

        try {
            Config.init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Verifica que la heladera es dada de alta")
    public void heladeraDadaDeAlta(){
        Assertions.assertTrue(heladera.estaActivaHeladera());
    }

    @Test
    @DisplayName("Verifica que la heladera es dada de baja")
    public void heladeraDadaDeBaja(){
        heladera.cambiarEstadoAFueraDeServicio();
        Assertions.assertEquals(heladera.getEstadoHeladera(), EstadoHeladera.FUERA_DE_SERVICIO);
    }

    @Test
    @DisplayName("Verifica que algunos atributos de la heladera pueden cambiar")
    public void heladeraModificada(){
        heladera.setNombreIdentificador("Heladera Campus");
        heladera.setUbicacion(new Ubicacion(-34.6596012f,-58.4705505f,(new Calle("Mozart","2300"))));
        Assertions.assertNotEquals(ubicacion, heladera.getUbicacion());
    }

    @Test
    @DisplayName("Verifica que los estados se guarden correctamente")
    public void historialDeEstados(){
        heladera.cambiarEstadoAInactiva();
        heladera.cambiarEstadoAActiva();
        heladera.cambiarEstadoAFueraDeServicio();
        Assertions.assertEquals(heladera.getHistorialDeEstados().size(), 4);
    }

    @Test
    @DisplayName("Muestra en pantalla el historial de estados")
    public void mostrarHistorialDeEstados() {
        heladera.cambiarEstadoAInactiva();
        heladera.cambiarEstadoAActiva();
        heladera.cambiarEstadoAInactiva();
        heladera.cambiarEstadoAFueraDeServicio();

        List<String> historial = heladera.getHistorialDeEstados();

        // Imprimir el historial de estados en la consola
        System.out.println("Historial de estados de la heladera:");
        for (String estado : historial) {
            System.out.println(estado);
        }

    }

    @Test
    public void aperturaHeladeraHabilitada() {
        SolicitudApertura solicitud = new SolicitudApertura(LocalDateTime.now(), "Apertura heladera para ingresar vianda.", lalo);
        heladera.registrarSolicitud(solicitud);

        assertDoesNotThrow(() -> heladera.registrarApertura(solicitud));
        assertNotNull(solicitud.getFechaHoraConcretado());
    }

    @Test
    public void aperturaHeladeraInhabilitada() {
        SolicitudApertura solicitud = new SolicitudApertura(LocalDateTime.now().minusMinutes(181), "Apertura heladera para ingresar vianda.", lalo);
        heladera.registrarSolicitud(solicitud);

        assertThrows(Exception.class, () -> heladera.registrarApertura(solicitud));
    }

}
