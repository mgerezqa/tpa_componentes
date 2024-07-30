package Tarjeta;

import domain.geografia.Calle;
import domain.geografia.Ubicacion;
import domain.heladera.Heladera.Heladera;
import domain.heladera.Heladera.ModeloDeHeladera;
import domain.heladera.Sensores.SensorMovimiento;
import domain.heladera.Sensores.SensorTemperatura;
import domain.persona.Persona;
import domain.persona.PersonaVulnerable;
import domain.tarjeta.RegistroDeUso;
import domain.tarjeta.Tarjeta;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class TestsTarjeta {
    Tarjeta tarjeta;
    PersonaVulnerable diego;
    Persona manuel;
    Persona miguel;
    Persona ken;
    Persona juan;
    LocalDate nacimiento;
    Ubicacion ubicacionDeHeladera;
    Heladera heladeraDeMedrano;
    SensorTemperatura sensorTemperatura;
    SensorMovimiento sensorMovimiento;
    Float tempMin = -10f;
    Float tempMax = 20f;
    ModeloDeHeladera modeloHeladera;

    @BeforeEach
    public void init(){
        nacimiento = LocalDate.of(2001,7,27);
        diego = new PersonaVulnerable("Diego",nacimiento);
        manuel = new Persona("manuel", 11);
        miguel = new Persona("miguel", 12);
        juan = new Persona("juan", 14);
        ken = new Persona("ken", 15);
        diego.agregarMenorACargo(manuel);
        diego.agregarMenorACargo(miguel);
        diego.agregarMenorACargo(juan);
        diego.agregarMenorACargo(ken);
        tarjeta = new Tarjeta(diego);
        ubicacionDeHeladera = new Ubicacion(522.00f,242.00f,new Calle("medrano","254"));
        modeloHeladera = new ModeloDeHeladera("Modelo XG-295");
        modeloHeladera.setTemperaturaMinima(tempMin);
        modeloHeladera.setTemperaturaMaxima(tempMax);
        //LocalDate fechaInicioFunc = LocalDate.of("2020","7","21");
        heladeraDeMedrano = new Heladera(modeloHeladera,"Heladera medrano",ubicacionDeHeladera);

    }
    @Test
    @DisplayName("La cantidad disponible para una persona vulnerable con 4 menores a cargo es 12 por dia")
    void cantidadDisponible(){
        Assertions.assertEquals(12,tarjeta.cantidadLimiteDisponiblePorDia());
    }
    @Test
    @DisplayName("La tarjeta tiene cantidad disponible en el dia")
    void validarCantidadDisponible(){
        Assertions.assertTrue(tarjeta.tieneCantidadDisponible());
    }
    @Test
    @DisplayName("La tarjeta no tiene mas cantidad disponible en el dia")
    void validarQueNoTengaCantidadDisponible(){
        tarjeta.setCantidadUsadaEnElDia(tarjeta.cantidadLimiteDisponiblePorDia());
        Assertions.assertFalse(tarjeta.tieneCantidadDisponible());
    }
    @Test
    @DisplayName("Se registra un nuevo registro al usar la tarjeta")
    void validarQueRegistroFueAgregadoAlUsarTarjeta() throws Exception{
        RegistroDeUso nuevoRegistro = new RegistroDeUso(heladeraDeMedrano);
        tarjeta.usoDeTarjeta(nuevoRegistro);
        Assertions.assertEquals(heladeraDeMedrano, tarjeta.getRegistros().get(0).getHeladera());
        Assertions.assertEquals(RegistroDeUso.class, tarjeta.getRegistros().get(0).getClass());
    }
    @Test
    @DisplayName("Se aumenta la cantidadUsadaEnElDia porque se registro un nuevo uso de la tarjeta")
    void validarCantidadDisponibleSiSeUsoLaTarjeta() throws Exception{
        RegistroDeUso nuevoRegistro = new RegistroDeUso(heladeraDeMedrano);
        tarjeta.usoDeTarjeta(nuevoRegistro);
        Assertions.assertEquals(1,tarjeta.getCantidadUsadaEnElDia());
    }
    @Test
    @DisplayName("No se realizo correctamente el uso de la tarjeta, por falta de cantidad disponible")
    void validarExceptionPorNoDisponibilidadDeTarjeta(){
        RegistroDeUso nuevoRegistro = new RegistroDeUso(heladeraDeMedrano);
        tarjeta.setCantidadUsadaEnElDia(tarjeta.cantidadLimiteDisponiblePorDia());
        Exception exception = Assertions.assertThrows(Exception.class,()->{
            tarjeta.usoDeTarjeta(nuevoRegistro);
        });
        String expectedMessage = "No hay más cantidad disponible por hoy!";
        String actualMessage = exception.getMessage();

        Assertions.assertEquals(expectedMessage,actualMessage);
    }
    @Test
    @DisplayName("Se valida que la cantidadUsadaEnElDia sea acorde con el numero de uso de la tarjeta")
    void validarCantidaUsadaEnElDiaConNumeroDeUsoDeTarjeta() throws Exception{
        RegistroDeUso registro1 = new RegistroDeUso(heladeraDeMedrano);
        RegistroDeUso registro2 = new RegistroDeUso(heladeraDeMedrano);
        RegistroDeUso registro3 = new RegistroDeUso(heladeraDeMedrano);
        tarjeta.usoDeTarjeta(registro1);
        tarjeta.usoDeTarjeta(registro2);
        tarjeta.usoDeTarjeta(registro3);
        Assertions.assertEquals(3,tarjeta.getCantidadUsadaEnElDia());
    }
    @Test
    @DisplayName("Se realizo correctamente el reseteo de la cantidadUsadaEnElDia a 0")
    void validarElResetDeLaCantidadUsadaEnElDia() throws Exception{
        RegistroDeUso registro1 = new RegistroDeUso(heladeraDeMedrano);
        RegistroDeUso registro2 = new RegistroDeUso(heladeraDeMedrano);
        RegistroDeUso registro3 = new RegistroDeUso(heladeraDeMedrano);
        tarjeta.usoDeTarjeta(registro1);
        tarjeta.usoDeTarjeta(registro2);
        tarjeta.usoDeTarjeta(registro3);
        tarjeta.resetCantidadUsadaEnElDia();
        Assertions.assertEquals(0,tarjeta.getCantidadUsadaEnElDia());
    }
}
