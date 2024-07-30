package GeoTests;

import utils.recomendacionDePuntos.Entidades.ListadoDePuntos;
import utils.recomendacionDePuntos.Entidades.Punto;
import utils.recomendacionDePuntos.Servicios.ServicioRecomendacionDePuntos;
import utils.recomendacionDePuntos.Servicios.ServicioRecomendacionDePuntosInterface;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServicioRecomendacionDePuntosTest {

    @Mock
    ServicioRecomendacionDePuntosInterface mockService;

    @InjectMocks
    ServicioRecomendacionDePuntos servicio;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Me aseguro de que la API me devuelva puntos recomendados dada una ubicacion")
    public void testListadoDePuntos() throws IOException {
        // Datos de prueba
        Double latitud = -70.606722;
        Double longitud = -90.381592;
        Double radio = 30.50;

        // Creo una lista de puntos de ejemplo
        Punto punto1 = new Punto(-34.603722, -58.381592);
        Punto punto2 = new Punto(-34.609722, -58.385592);
        List<Punto> puntosRecomendados = Arrays.asList(punto1, punto2);
        ListadoDePuntos listadoDePuntos = new ListadoDePuntos();
        listadoDePuntos.puntosRecomendados = puntosRecomendados;

        // Mockear el comportamiento de la llamada a la API
        Call<ListadoDePuntos> mockCall = mock(Call.class);
        when(mockService.puntosRecomendados(latitud, longitud, radio)).thenReturn(mockCall);
        when(mockCall.execute()).thenReturn(Response.success(listadoDePuntos));

        // Llamar al método que queremos testear
        ListadoDePuntos result = servicio.listadoDePuntos(latitud, longitud, radio);

        // Verificar el resultado
        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.getPuntosRecomendados().size());
        //Para el punto 1
        Assertions.assertEquals(punto1.getLatitud(), result.getPuntosRecomendados().get(0).getLatitud(), radio);
        Assertions.assertEquals(punto1.getLongitud(), result.getPuntosRecomendados().get(0).getLongitud(),radio);
        //Para el punto 2
        Assertions.assertEquals(punto2.getLatitud(), result.getPuntosRecomendados().get(1).getLatitud(),radio);
        Assertions.assertEquals(punto2.getLongitud(), result.getPuntosRecomendados().get(1).getLongitud(),radio);
    }
}

