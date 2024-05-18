

import domain.geografia.Calle;
import domain.geografia.Ubicacion;
import domain.heladera.Heladera;
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
    domain.tarjeta.Tarjeta tarjeta;
    PersonaVulnerable diego;
    Persona manuel;
    Persona miguel;
    Persona ken;
    Persona juan;
    LocalDate nacimiento;
    Ubicacion ubicacionDeHeladera;
    Heladera heladeraDeMedrano;
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
        heladeraDeMedrano = new Heladera(ubicacionDeHeladera,"heladera medrano", 20,LocalDate.of(2020,7,15),60f,20f);
    }
    @Test
    @DisplayName("La cantidad disponible para una persona vulnerable con 4 menores a cargo es 12 por dia")
    void cantidadDisponible(){
        Assertions.assertEquals(12,tarjeta.getCantidadTotalDisponibleActualmente());
    }
    @Test
    @DisplayName("La tarjeta tiene cantidad disponible en el dia")
    void validarCantidadDisponible(){
        Assertions.assertTrue(tarjeta.tieneCantidadDisponible());
    }
    @Test
    @DisplayName("La tarjeta no tiene mas cantidad disponible en el dia")
    void validarQueNoTengaCantidadDisponible(){
        tarjeta.setCantidadTotalDisponibleActualmente(0);
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
    @DisplayName("Se disminuye la cantidadDisponible porque se registro un nuevo uso de la tarjeta")
    void validarCantidadDisponibleSiSeUsoLaTarjeta() throws Exception{
        RegistroDeUso nuevoRegistro = new RegistroDeUso(heladeraDeMedrano);
        tarjeta.usoDeTarjeta(nuevoRegistro);
        Assertions.assertEquals(11,tarjeta.getCantidadTotalDisponibleActualmente());
    }
    @Test
    @DisplayName("No se realizo correctamente el uso de la tarjeta, por falta de cantidad disponible")
    void validarExceptionPorNoDisponibilidadDeTarjeta(){
        RegistroDeUso nuevoRegistro = new RegistroDeUso(heladeraDeMedrano);
        tarjeta.setCantidadTotalDisponibleActualmente(0);
        Exception exception = Assertions.assertThrows(Exception.class,()->{
            tarjeta.usoDeTarjeta(nuevoRegistro);
        });
        String expectedMessage = "No hay más cantidad disponible por hoy!";
        String actualMessage = exception.getMessage();

        Assertions.assertEquals(expectedMessage,actualMessage);
    }
}
