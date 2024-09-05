package Persistencia;

import domain.geografia.Calle;
import domain.geografia.Ubicacion;
import domain.persona.Persona;
import domain.persona.PersonaVulnerable;
import domain.tarjeta.Tarjeta;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repositorios.Repositorio;

import java.time.LocalDate;

public class TestsTarjeta implements WithSimplePersistenceUnit {
    Tarjeta tarjeta;
    PersonaVulnerable diego;
    Persona manuel;
    Persona miguel;
    Persona ken;
    Persona juan;
    LocalDate nacimiento;
    Ubicacion ubicacionDeHeladera;
    Repositorio repositorio;
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
    }

}
