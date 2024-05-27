package domain;

import domain.contacto.Email;
import domain.contacto.MedioDeContacto;
import domain.donaciones.*;
import domain.geografia.Calle;
import domain.geografia.Ubicacion;
import domain.heladera.Heladera.Heladera;
import domain.heladera.Heladera.ModeloDeHeladera;
import domain.heladera.Sensores.SensorMovimiento;
import domain.heladera.Sensores.SensorTemperatura;
import domain.usuarios.ColaboradorFisico;
import domain.usuarios.ColaboradorJuridico;
import domain.usuarios.Rubro;
import domain.usuarios.TipoRazonSocial;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DonacionesTests {
    private ColaboradorFisico lalo;
    private ColaboradorJuridico metrovias;
    private LocalDate fechaDeDonacion;
    private LocalDate fechaVencimiento;
    private LocalDate fechaInicioFuncionamiento;
    private MedioDeContacto laloEmail;
    private Heladera heladera;
    private Ubicacion ubicacion;
    private SensorMovimiento sensorMovimiento;
    private SensorTemperatura sensorTemperatura;
    private ModeloDeHeladera modelo;
    private Float tempMin = -10f;
    private Float tempMax = 20f;

    @BeforeEach
    public void setUp() {
        this.laloEmail = new Email("lalo@gmail.com");
        this.fechaVencimiento = LocalDate.of(2024, 5, 31); // Ejemplo de fecha de vencimiento
        this.fechaDeDonacion = LocalDate.now(); // Ejemplo de fecha de donación (fecha actual)
        this.fechaInicioFuncionamiento = LocalDate.of(2021, 5, 31); // Ejemplo de fecha de inicio de funcionamiento
        this.lalo = new ColaboradorFisico("Lalo", "Menz",laloEmail);
        this.metrovias = new ColaboradorJuridico("Metrovias S.A",TipoRazonSocial.EMPRESA, Rubro.SERVICIOS,laloEmail);
        this.ubicacion = new Ubicacion(-54F,-48F,new Calle("Av. Rivadavia", "1234"));

        modelo = new ModeloDeHeladera("Modelo XR-221");
        modelo.setTemperaturaMinima(tempMin);
        modelo.setTemperaturaMaxima(tempMax);
        this.heladera = new Heladera(ubicacion,"Heladera Palermo",200, fechaInicioFuncionamiento, sensorMovimiento, sensorTemperatura, modelo);
    }


    // Test
    @Test
    @DisplayName("El sistema admite las donaciones de dinero por parte de los colaboradores fisicos")
    public void donarDineroColaboradorFisico(){

        Dinero dineroDonadoPorColaboradorFisico = new Dinero(2000, FrecuenciaDeDonacion.FRECUENCIA_ANUAL,fechaDeDonacion,lalo);
        assertEquals(dineroDonadoPorColaboradorFisico.getColaboradorQueLaDono(),lalo);

    }

    @Test
    @DisplayName("El sistema admite las donaciones de dinero por parte de los colaboradores juridicos")
    public void donarDineroColaboradorJuridico(){

        Dinero dineroDonadoPorColaboradorJuridico = new Dinero(2000, FrecuenciaDeDonacion.FRECUENCIA_ANUAL,fechaDeDonacion,metrovias);
        assertEquals(dineroDonadoPorColaboradorJuridico.getColaboradorQueLaDono(),metrovias);

    }

    @Test
    @DisplayName("El sistema solo admite las donaciones de viandas por parte de los colaboradores fisicos")
    public void donarViandaColaboradorFisico(){

        Vianda viandaDonadaPorColaboradorFisico = new Vianda(fechaVencimiento,fechaDeDonacion,lalo);
        assertEquals(viandaDonadaPorColaboradorFisico.getColaboradorQueLaDono(),lalo);

    }

    @Test
    @DisplayName("El sistema solo admite las donaciones de tipo distribución de viandas por parte de los colaboradores fisicos")
    public void donarDistribucionDeViandas(){

        Distribuir distribucionDeViandas = new Distribuir(heladera,heladera,10, Motivo.FALTA_DE_VIANDAS,fechaDeDonacion,lalo);
        assertEquals(distribucionDeViandas.getColaboradorQueLaDono(),lalo);

    }

    @Test
    @DisplayName("El sistema admite las donaciones del tipo mantenimiento de una heladera solo por parte de los colaboradores jurídicos")
    public void donarMantenerHeladera(){
        MantenerHeladera mantenerHeladera = new MantenerHeladera(heladera, fechaDeDonacion,metrovias);
        assertEquals(mantenerHeladera.getColaboradorQueLaDono(),metrovias);
    }

}
