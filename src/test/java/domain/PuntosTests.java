package domain;

import domain.contacto.Email;
import domain.donaciones.*;
import domain.geografia.Calle;
import domain.geografia.Ubicacion;
import domain.heladera.Heladera.Heladera;
import domain.heladera.Heladera.ModeloDeHeladera;
import domain.heladera.Sensores.SensorMovimiento;
import domain.heladera.Sensores.SensorTemperatura;
import domain.persona.PersonaVulnerable;
import domain.puntos.*;
import domain.tarjeta.Tarjeta;
import domain.usuarios.ColaboradorFisico;
import domain.usuarios.ColaboradorJuridico;
import domain.usuarios.Rubro;
import domain.usuarios.TipoRazonSocial;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repositorios.Repositorio;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

public class PuntosTests {
    private Long id;
    private CalculadoraPuntos calculadoraPuntos;
    private ColaboradorFisico lalo;
    private ColaboradorJuridico restaurant;
    private Email laloEmail;
    private Ubicacion ubicacion;
    private SensorMovimiento sensorMovimiento;
    private SensorTemperatura sensorTemperatura;
    private ModeloDeHeladera modeloHeladera;
    private Heladera heladeraPalermo;
    private Heladera heladeraMedrano;
    private Config config;
    private PersonaVulnerable carlos;
    private PersonaVulnerable marcos;

    @BeforeEach
    public void setUp() throws IOException {

        config = Config.getInstance();
        this.carlos = new PersonaVulnerable("Carlos", LocalDate.of(1960, 5, 12));
        this.marcos = new PersonaVulnerable("Marcos", LocalDate.of(1976, 6, 3));

        this.calculadoraPuntos = new CalculadoraPuntos();

        this.laloEmail = mock(Email.class);
        this.lalo = new ColaboradorFisico("Lalo", "Menz");
        this.restaurant = new ColaboradorJuridico("Restaurant", TipoRazonSocial.EMPRESA, Rubro.SERVICIOS);

        this.id = 564564L;

        this.ubicacion = new Ubicacion(-54F, -48F, new Calle("Av. Rivadavia", "1234"));
        this.modeloHeladera = new ModeloDeHeladera("Modelo X-R98");

        LocalDate fechaInicioFuncMed = LocalDate.parse("2023-01-01");
        LocalDate fechaInicioFuncPal = LocalDate.parse("2024-01-01");
        Integer capacidadMax = 200;

        this.heladeraMedrano = new Heladera(modeloHeladera,"Heladera medrano", ubicacion);
        this.heladeraPalermo = new Heladera(modeloHeladera,"Heladera Palermo", ubicacion);
        this.sensorMovimiento = new SensorMovimiento(heladeraMedrano);
        this.sensorTemperatura = new SensorTemperatura(heladeraPalermo);

        heladeraPalermo.setCapacidadMax(200);
        heladeraMedrano.setCapacidadMax(180);
        LocalDate fechaInicGenerica = LocalDate.now();
        heladeraMedrano.setFechaInicioFuncionamiento(fechaInicGenerica);
        heladeraPalermo.setFechaInicioFuncionamiento(fechaInicGenerica);
    }

    @Test
    @DisplayName("Probando archivo de configuracion")
    public void lecturaArchivoProperties() {
        try {
            System.out.println("TestProperties");
            System.out.println("Coeficiente de dinero donado: " + config.getProperty("puntos.coefDineroDonado"));
            System.out.println("Coeficiente de viandas distribuidas: " + config.getProperty("puntos.coefViandasDistribuidas"));
            System.out.println("Coeficiente de viandas donadas: " + config.getProperty("puntos.coefViandasDonadas"));
            System.out.println("Coeficiente de tarjetas repartidas: " + config.getProperty("puntos.coefTarjetasRepartidas"));
            System.out.println("Coeficiente de heladeras activas: " + config.getProperty("puntos.coefHeladerasActivas"));
            System.out.println("--------------------------------------------------");
        } catch (NumberFormatException e) {
            System.err.println("Error en el formato del archivo de propiedades: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Controlar puntos que se otorgan")
    public void controlCalculosPuntos() {
        Dinero donacion = new Dinero(4000, FrecuenciaDeDonacion.FRECUENCIA_ANUAL, LocalDate.now(), lalo);
        Distribuir distribucionViandas = new Distribuir(heladeraMedrano, heladeraPalermo,10,LocalDate.now(),lalo);
        MantenerHeladera mantenerHeladera = new MantenerHeladera(heladeraMedrano, LocalDate.now().minusMonths(3), restaurant);

        assertEquals(2000, calculadoraPuntos.puntosPesosDonados(donacion));
        assertEquals(10, calculadoraPuntos.puntosViandasDistribuidas(distribucionViandas));
        assertEquals(3, calculadoraPuntos.puntosViandasDonadas(2));
        assertEquals(4, calculadoraPuntos.puntosTarjetasRepatidas(2));
        assertEquals(15, calculadoraPuntos.puntosHeladerasActivas(mantenerHeladera));
        //Debe dar 0 puntos porque ya se otorgaron los puntos correspondientes a los meses
        assertEquals(0, calculadoraPuntos.puntosHeladerasActivas(mantenerHeladera));
    }

    @Test
    @DisplayName("Se otorga puntos por donacion de dinero")
    public void puntosDonarViandaColaboradores() {
        Dinero donacionFisico = new Dinero(4000, FrecuenciaDeDonacion.FRECUENCIA_ANUAL, LocalDate.now(), lalo);
        int puntosFisico = calculadoraPuntos.puntosPesosDonados(donacionFisico);
        lalo.sumarPuntos(puntosFisico);

        Dinero donacionJuridico = new Dinero(10000, FrecuenciaDeDonacion.FRECUENCIA_ANUAL, LocalDate.now(), restaurant);
        int puntosJuridico = calculadoraPuntos.puntosPesosDonados(donacionJuridico);
        restaurant.sumarPuntos(puntosJuridico);

        assertEquals(puntosFisico, lalo.getPuntosAcumulados());
        assertEquals(puntosJuridico, restaurant.getPuntosAcumulados());
    }

    @Test
    @DisplayName("Se otorga puntos por viandas distribuidas")
    public void puntosDistribuirViandas() {
        Distribuir distribucionViandas = new Distribuir(heladeraMedrano, heladeraPalermo,10,LocalDate.now(), lalo);
        int puntos = calculadoraPuntos.puntosViandasDistribuidas(distribucionViandas);
        lalo.sumarPuntos(puntos);

        assertEquals(puntos, lalo.getPuntosAcumulados());
    }

    @Test
    @DisplayName("Se otorga puntos por viandas donadas")
    public void puntosDonacionViandas() {
        Vianda milanesa = new Vianda(LocalDate.parse("2099-01-01"), LocalDate.now(), lalo);
        Vianda risotto = new Vianda(LocalDate.parse("2099-01-01"), LocalDate.now(), lalo);
        int puntos = calculadoraPuntos.puntosViandasDonadas(2);
        lalo.sumarPuntos(puntos);

        assertEquals(puntos, lalo.getPuntosAcumulados());
    }

    @Test
    @DisplayName("Se otorga puntos por tarjetas repartidas")
    public void donacionTarjetas() {

        Tarjeta tarjetaCarlos = new Tarjeta();
        Tarjeta tarjetaMarcos = new Tarjeta();
        RegistroDePersonaVulnerable entregaTarjetaPablo = new RegistroDePersonaVulnerable(lalo, tarjetaCarlos, carlos, LocalDate.now());
        RegistroDePersonaVulnerable entregaTarjetaMarcos = new RegistroDePersonaVulnerable(lalo, tarjetaMarcos, marcos, LocalDate.now());

        int puntos = calculadoraPuntos.puntosTarjetasRepatidas(2);
        lalo.sumarPuntos(puntos);

        assertEquals(puntos, lalo.getPuntosAcumulados());
    }

    @Test
    @DisplayName("Se otorga puntos por heladeras activas")
    public void puntosMantenerHeladeras() {
        MantenerHeladera mantenerHeladeraMedrano = new MantenerHeladera(heladeraMedrano, LocalDate.parse("2023-01-01"), restaurant);
        int puntos = calculadoraPuntos.puntosHeladerasActivas(mantenerHeladeraMedrano);
        restaurant.sumarPuntos(puntos);

        assertEquals(puntos, restaurant.getPuntosAcumulados());
    }

    @Test
    @DisplayName("Se otorga puntos por multiples donaciones")
    public void puntosMultiples() {
        MantenerHeladera mantenerHeladeraMedrano = new MantenerHeladera(heladeraMedrano, LocalDate.parse("2023-01-01"), restaurant);
        int puntosHeladera = calculadoraPuntos.puntosHeladerasActivas(mantenerHeladeraMedrano);
        restaurant.sumarPuntos(puntosHeladera);

        Dinero donacion = new Dinero(10000, FrecuenciaDeDonacion.FRECUENCIA_ANUAL, LocalDate.now(), restaurant);
        int puntosDinero = calculadoraPuntos.puntosPesosDonados(donacion);
        restaurant.sumarPuntos(puntosDinero);

        assertEquals(puntosHeladera+puntosDinero, restaurant.getPuntosAcumulados());
    }

    @Test
    @DisplayName("Se canjea un producto")
    public void canjearProductoOServicio()
    {
        Dinero donacion = new Dinero(4000, FrecuenciaDeDonacion.FRECUENCIA_ANUAL, LocalDate.now(), lalo);
        int puntos = calculadoraPuntos.puntosPesosDonados(donacion);
        lalo.sumarPuntos(puntos);

        Oferta ofertaAlmohada = new Oferta("Almohada Coinor", "Almohada Suave", CategoriaOferta.ARTICULOS_HOGAR, restaurant, 2000);

        Assertions.assertDoesNotThrow(() -> ofertaAlmohada.hacerCanje(lalo, ofertaAlmohada));
        assertEquals(puntos-ofertaAlmohada.getCostoPuntos(), lalo.getPuntosAcumulados());
    }

    @Test
    @DisplayName("Se intenta canjear un producto pero no alcanza")
    public void falloCanjearProductoOServicio()
    {
        Dinero donacion = new Dinero(4000, FrecuenciaDeDonacion.FRECUENCIA_ANUAL, LocalDate.now(), lalo);
        int puntos = calculadoraPuntos.puntosPesosDonados(donacion);
        lalo.sumarPuntos(puntos);

        Oferta ofertaPS5 = new Oferta("Playstation 5", "La play 5", CategoriaOferta.ELECTRONICA, restaurant, 10000);

        Assertions.assertThrows(Exception.class, () -> ofertaPS5.hacerCanje(lalo, ofertaPS5));

        assertEquals(puntos, lalo.getPuntosAcumulados());
    }
}
