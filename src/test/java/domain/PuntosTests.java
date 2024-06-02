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

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PuntosTests {

    private CalculadoraPuntos calculadoraPuntos;
    private CatalogoPuntos catalogo;
    private Oferta ofertaServicio;
    private ColaboradorFisico lalo;
    private ColaboradorJuridico restaurant;
    private Email laloEmail;
    private Ubicacion ubicacion;
    private SensorMovimiento sensorMovimiento;
    private SensorTemperatura sensorTemperatura;
    private ModeloDeHeladera modeloHeladera;
    private Heladera heladeraPalermo;
    private Heladera heladeraMedrano;


    @BeforeEach
    public void setUp() throws IOException {
        CalculadoraPuntosConfigLoader.init();

        this.calculadoraPuntos = new CalculadoraPuntos();
        this.catalogo = new CatalogoPuntos();

        this.laloEmail = new Email("lalo@gmail.com");
        this.lalo = new ColaboradorFisico("Lalo", "Menz", laloEmail);
        this.restaurant = new ColaboradorJuridico("Restaurant", TipoRazonSocial.EMPRESA, Rubro.SERVICIOS, laloEmail);

        this.ubicacion = new Ubicacion(-54F, -48F, new Calle("Av. Rivadavia", "1234"));
        this.sensorMovimiento = new SensorMovimiento();
        this.sensorTemperatura = new SensorTemperatura();
        this.modeloHeladera = new ModeloDeHeladera("Modelo X-R98");

        LocalDate fechaInicioFuncMed = LocalDate.parse("2023-01-01");
        LocalDate fechaInicioFuncPal = LocalDate.parse("2024-01-01");
        Integer capacidadMax = 200;

        this.heladeraMedrano = new Heladera(modeloHeladera,"Heladera medrano", ubicacion, sensorMovimiento, sensorTemperatura);
        this.heladeraPalermo = new Heladera(modeloHeladera,"Heladera Palermo", ubicacion, sensorMovimiento, sensorTemperatura);

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
            CalculadoraPuntosConfigLoader.init();
            System.out.println("Coeficiente de dinero donado: " + CalculadoraPuntosConfigLoader.getDoubleProperty("coeficiente.dineroDonado"));
            System.out.println("Coeficiente de viandas distribuidas: " + CalculadoraPuntosConfigLoader.getDoubleProperty("coeficiente.viandasDistribuidas"));
            System.out.println("Coeficiente de viandas donadas: " + CalculadoraPuntosConfigLoader.getDoubleProperty("coeficiente.viandasDonadas"));
            System.out.println("Coeficiente de tarjetas repartidas: " + CalculadoraPuntosConfigLoader.getDoubleProperty("coeficiente.tarjetasRepartidas"));
            System.out.println("Coeficiente de heladeras activas: " + CalculadoraPuntosConfigLoader.getDoubleProperty("coeficiente.heladerasActivas"));
            System.out.println("--------------------------------------------------");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.err.println("Error en el formato del archivo de propiedades: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Se otorga puntos por donacion de dinero")
    public void puntosDonarViandaColaboradores() {
        Dinero donacionFisico = new Dinero(4000, FrecuenciaDeDonacion.FRECUENCIA_ANUAL, LocalDate.now(), lalo);
        int puntosFisico = calculadoraPuntos.puntosPesosDonados(donacionFisico.getCantidad());
        lalo.sumarPuntos(puntosFisico);

        Dinero donacionJuridico = new Dinero(10000, FrecuenciaDeDonacion.FRECUENCIA_ANUAL, LocalDate.now(), restaurant);
        int puntosJuridico = calculadoraPuntos.puntosPesosDonados(donacionJuridico.getCantidad());
        restaurant.sumarPuntos(puntosJuridico);

        assertEquals((int) Math.round(4000*CalculadoraPuntosConfigLoader.getDoubleProperty("coeficiente.dineroDonado")), lalo.getPuntosAcumulados());
        assertEquals((int) Math.round(10000*CalculadoraPuntosConfigLoader.getDoubleProperty("coeficiente.dineroDonado")), restaurant.getPuntosAcumulados());
    }

    @Test
    @DisplayName("Se otorga puntos por viandas distribuidas")
    public void puntosDistribuirViandas() {
        Distribuir distribucionViandas = new Distribuir(heladeraMedrano, heladeraPalermo,10, Motivo.FALTA_DE_VIANDAS, LocalDate.now(),lalo);
        int puntos = calculadoraPuntos.puntosViandasDistribuidas(distribucionViandas.getCantidad());
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

        Tarjeta tarjetaPablo = new Tarjeta(new PersonaVulnerable("Pablo", LocalDate.parse("2023-01-01")));
        Tarjeta tarjetaMarcos = new Tarjeta(new PersonaVulnerable("Marcos", LocalDate.parse("2024-01-01")));
        RegistroDePersonaVulnerable entregaTarjetaPablo = new RegistroDePersonaVulnerable(lalo, tarjetaPablo);
        RegistroDePersonaVulnerable entregaTarjetaMarcos = new RegistroDePersonaVulnerable(lalo, tarjetaMarcos);

        int puntos = calculadoraPuntos.puntosTarjetasRepatidas(2);
        lalo.sumarPuntos(puntos);

        assertEquals(puntos, lalo.getPuntosAcumulados());
    }

    @Test
    @DisplayName("Se otorga puntos por heladeras activas")
    public void puntosMantenerHeladeras() {
        MantenerHeladera mantenerHeladeraMedrano = new MantenerHeladera(heladeraMedrano, LocalDate.parse("2023-01-01"), restaurant);
        int puntos = calculadoraPuntos.puntosHeladerasActivas(1, heladeraMedrano.mesesActiva());
        restaurant.sumarPuntos(puntos);

        assertEquals(puntos, restaurant.getPuntosAcumulados());
    }

    @Test
    @DisplayName("Se otorga puntos por multiples donaciones")
    public void puntosMultiples() {
        MantenerHeladera mantenerHeladeraMedrano = new MantenerHeladera(heladeraMedrano, LocalDate.parse("2023-01-01"), restaurant);
        int puntosHeladera = calculadoraPuntos.puntosHeladerasActivas(1, heladeraMedrano.mesesActiva());
        restaurant.sumarPuntos(puntosHeladera);

        Dinero donacion = new Dinero(10000, FrecuenciaDeDonacion.FRECUENCIA_ANUAL, LocalDate.now(), restaurant);
        int puntosDinero = calculadoraPuntos.puntosPesosDonados(donacion.getCantidad());
        restaurant.sumarPuntos(puntosDinero);

        assertEquals(puntosHeladera+puntosDinero, restaurant.getPuntosAcumulados());
    }

    @Test
    @DisplayName("Se canjea un producto")
    public void canjearProductoOServicio()
    {
        Dinero donacion = new Dinero(4000, FrecuenciaDeDonacion.FRECUENCIA_ANUAL, LocalDate.now(), lalo);
        int puntos = calculadoraPuntos.puntosPesosDonados(donacion.getCantidad());
        lalo.sumarPuntos(puntos);

        Oferta ps5 = new Oferta("Playstation 5", "La play 5", CategoriaOferta.ELECTRONICA, restaurant, puntos/2);
        catalogo.agregarOferta(ps5);

        Assertions.assertDoesNotThrow(() -> catalogo.canjearPuntos(lalo.getPuntosAcumulados(), ps5));
        lalo.restarPuntos(ps5.costoPuntos);

        assertEquals(puntos-ps5.costoPuntos, lalo.getPuntosAcumulados());
    }

    @Test
    @DisplayName("Se intenta canjear un producto pero no alcanza")
    public void falloCanjearProductoOServicio()
    {
        Dinero donacion = new Dinero(4000, FrecuenciaDeDonacion.FRECUENCIA_ANUAL, LocalDate.now(), lalo);
        int puntos = calculadoraPuntos.puntosPesosDonados(donacion.getCantidad());
        lalo.sumarPuntos(puntos);

        Oferta ps5 = new Oferta("Playstation 5", "La play 5", CategoriaOferta.ELECTRONICA, restaurant, puntos*2);
        catalogo.agregarOferta(ps5);

        Assertions.assertThrows(Exception.class, () -> catalogo.canjearPuntos(lalo.getPuntosAcumulados(), ps5));

        assertEquals(puntos, lalo.getPuntosAcumulados());
    }
}
