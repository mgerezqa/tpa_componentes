package GeoTests;

import utils.recomendacionDePuntos.Entidades.ListadoDePuntos;
import utils.recomendacionDePuntos.Entidades.Punto;
import utils.recomendacionDePuntos.Servicios.Adapter.RecomendacionDePuntosAdapter;
import utils.recomendacionDePuntos.Servicios.ServicioRecomendacionDePuntos;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import retrofit2.Call;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RecomendacionDePuntosAdapterTest {

    @Mock
    ServicioRecomendacionDePuntos mockServicio;

    @InjectMocks
    RecomendacionDePuntosAdapter adapter;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Me aseguro de que la API me devuelva los puntos recomendados dada una ubicación")
    public void testObtenerPuntosRecomendados() throws IOException {
        // Datos de prueba
        Double latitud = -70.606722;
        Double longitud = -90.381592;
        Double radio = 0.0;

        // Crear lista de puntos de ejemplo que se supone la API ya tiene
        Punto punto1 = new Punto(-34.5534253598, -58.15234532426);
        Punto punto2 = new Punto(-19.604, -88.422);
        List<Punto> puntosRecomendados = Arrays.asList(punto1, punto2);
        ListadoDePuntos listadoDePuntos = new ListadoDePuntos();
        listadoDePuntos.puntosRecomendados = puntosRecomendados;

        // Mockear el comportamiento de la llamada a la API
        Call<ListadoDePuntos> mockCall = mock(Call.class);
        when(mockServicio.listadoDePuntos(latitud, longitud, radio)).thenReturn(listadoDePuntos);

        // Llamar al método que queremos testear
        List<Punto> result = adapter.obtenerPuntosRecomendados(latitud, longitud, radio);

        // Verificar el resultado
        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
        // Para el punto 1
        Assertions.assertEquals(punto1.getLatitud(), result.get(0).getLatitud(), 0.001);
        Assertions.assertEquals(punto1.getLongitud(), result.get(0).getLongitud(), 0.001);
        // Para el punto 2
        Assertions.assertEquals(punto2.getLatitud(), result.get(1).getLatitud(), 0.001);
        Assertions.assertEquals(punto2.getLongitud(), result.get(1).getLongitud(), 0.001);
    }
}
