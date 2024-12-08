package PersonaVulnerable;

import domain.persona.Persona;
import domain.persona.PersonaVulnerable;
import org.junit.jupiter.api.*;

import java.time.LocalDate;

@Disabled
public class TestPersonaVulnerable {
    Persona mateo;
    Persona diego;
    PersonaVulnerable maria;
    @BeforeEach
    public void init() {
         mateo = new Persona("Mateo", 12);
         diego = new Persona("Diego", 11);
         maria = new PersonaVulnerable("Maria", LocalDate.of(1996, 7, 21));
         maria.agregarMenorACargo(mateo);
         maria.agregarMenorACargo(diego);
    }
    @Test
    @DisplayName("Persona vulnerable tiene a 2 menores a cargo")
    void personaVulnerableCon2MenoresACargo(){
        Assertions.assertEquals(maria.cantidadDeMenoresACargo(),2);
    }
}
