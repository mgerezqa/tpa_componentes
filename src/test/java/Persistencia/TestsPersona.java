package Persistencia;
import domain.persona.Persona;
import io.github.flbulgarelli.jpa.extras.test.SimplePersistenceTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repositorios.Repositorio;
import java.util.Optional;

public class TestsPersona implements SimplePersistenceTest {
    private Persona persona;
    private Repositorio repositorio;

    @Test
    @DisplayName("Se agrega correctamente una persona en el medio persistente")
    void guardado() {
        repositorio = new Repositorio();
        persona = new Persona("Nahuel", 12);
        persist(persona);
        Assertions.assertNotNull(persona.getId(), "El ID de la persona no debería ser nulo.");

        Optional<Object> personaGuardada = repositorio.buscarPorID(Persona.class, persona.getId());
        Assertions.assertTrue(personaGuardada.isPresent(), "La persona guardada debería estar presente.");

        Persona personaRecuperada = (Persona) personaGuardada.get();
        Assertions.assertEquals(persona.getNombre(), personaRecuperada.getNombre(), "Los nombres deberían ser iguales.");
        Assertions.assertEquals(persona.getEdad(), personaRecuperada.getEdad(), "Las edades deberían ser iguales.");
    }
}
