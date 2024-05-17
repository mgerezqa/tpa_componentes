package Tarjeta;

import domain.persona.PersonaVulnerable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class Tarjeta {
    Tarjeta tarjeta1;
    PersonaVulnerable diego;
    LocalDate nacimiento;
    LocalDate fechaRegistro;
    @BeforeEach
    public void init(){
        nacimiento = LocalDate.of(2001,7,27);
        fechaRegistro = LocalDate.now();
        diego = new PersonaVulnerable("Diego",nacimiento,fechaRegistro);
        manuel = new PersonaVulnerable()
        persona1.setCantidadDeMenoresACargo(4);
        tarjeta1 = new Tarjeta(persona1);
    }
    @Test
    @DisplayName("La cantidad disponible para una persona vulnerable con 4 menores a cargo es 12 por dia")
    void cantidadDisponible(){
        Assertions.assertEquals(12,tarjeta1.getCantidadTotalDisponibleActualmente());
    }
    @Test
    @DisplayName("La tarjeta tiene cantidad disponible en el dia")
    void validarCantidadDisponible(){
        Assertions.assertTrue(tarjeta1.tieneCantidadDisponible());
    }
    @Test
    @DisplayName("La tarjeta no tiene mas cantidad disponible en el dia")
    void validarQueNoTengaCantidadDisponible(){
        tarjeta1.setCantidadTotalDisponibleActualmente(0);
        Assertions.assertFalse(tarjeta1.tieneCantidadDisponible());
    }
    @Test
    @DisplayName("Se registra un nuevo registro en la tarjeta correctamente")
    void validarTarjetaFueUsada1vez(){
        Heladera heladera = new Heladera("Heladera de medrano");
        RegistroDeUsoDeTarjeta nuevoRegistro = new RegistroDeUsoDeTarjeta(heladera);
        tarjeta1.agregarRegistroDeUso(nuevoRegistro);
        Assertions.assertEquals(heladera, tarjeta1.getRegistros().get(0).getHeladera());
    }
    @Test
    @DisplayName("Se disminuye la cantidadDisponible porque se registro un nuevo uso de la tarjeta")
    void validarCantidadDisponibleSiSeUsoLaTarjeta(){
        Heladera heladera = new Heladera("Heladera de medrano");
        RegistroDeUsoDeTarjeta nuevoRegistro = new RegistroDeUsoDeTarjeta(heladera);
        tarjeta1.usoDeTarjeta(nuevoRegistro);
        Assertions.assertEquals(11,tarjeta1.getCantidadTotalDisponibleActualmente());
    }
}
