package domain;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import domain.heladera.Heladera.Heladera;
import domain.incidentes.Alerta;
import domain.incidentes.FallaTecnica;
import domain.incidentes.IncidenteFactory;
import domain.usuarios.Usuario;
import dtos.FallaTecnicaDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

public class AlarmasYFallasTecnicasTests {

    private Heladera heladera;
    private Usuario usuario;
    private FallaTecnicaDTO fallaTecnicaDTO;

    @BeforeEach
    public void setUp() {
        heladera = mock(Heladera.class);
        usuario = mock(Usuario.class);
        fallaTecnicaDTO = new FallaTecnicaDTO();
        fallaTecnicaDTO.setDescripcion("Descripción de prueba");
        fallaTecnicaDTO.setFoto("Foto de prueba");
    }

    @Test
    public void testCrearAlerta() {
        Alerta alerta = IncidenteFactory.crearAlerta(heladera, "falla_temperatura");
        assertEquals("falla_temperatura", alerta.getTipoAlerta());
        assertEquals(heladera, alerta.getHeladera());
    }

    @Test
    public void testCrearFallaTecnica() {
        FallaTecnica fallaTecnica = IncidenteFactory.crearFallaTecnica(fallaTecnicaDTO, heladera, usuario);
        assertEquals("Descripción de prueba", fallaTecnica.getDescripcion());
        assertEquals("Foto de prueba", fallaTecnica.getFoto());
        assertEquals(heladera, fallaTecnica.getHeladera());
        assertEquals(usuario, fallaTecnica.getReportadoPor());
    }


}
