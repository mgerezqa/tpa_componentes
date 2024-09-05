package Persistencia;

import domain.persona.Persona;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import org.junit.jupiter.api.Test;
import repositorios.Repositorio;

public class TestsPersona implements WithSimplePersistenceUnit {
    Persona persona;
    Repositorio repositorio;
    @Test
    void guardado(){
        repositorio = new Repositorio();
        persona = new Persona("Nahuel",12);
        withTransaction(()->{
            repositorio.guardar(persona);
        });
    }
}
