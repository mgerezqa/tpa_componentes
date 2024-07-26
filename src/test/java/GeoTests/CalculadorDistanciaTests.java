//package GeoTests;
//
//import domain.formulario.Cuil;
//import domain.formulario.Documento;
//import domain.formulario.TipoDocumento;
//import domain.geografia.Calle;
//import domain.geografia.Ubicacion;
//import domain.geografia.area.AreaDeCobertura;
//import domain.geografia.area.TamanioArea;
//import domain.heladera.Heladera.Heladera;
//import domain.heladera.Heladera.ModeloDeHeladera;
//import domain.heladera.Sensores.SensorTemperatura;
//import domain.incidentes.Alerta;
//import domain.incidentes.FallaTecnica;
//import domain.incidentes.IncidenteFactory;
//import domain.usuarios.Tecnico;
//import domain.usuarios.Usuario;
//import dtos.FallaTecnicaDTO;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import utils.calculadorDistancia.CalculadorDistanciaKM;
//import utils.calculadorDistancia.ICalculadorDistanciaKM;
//import utils.notificador.INotificadorIncidentes;
//import utils.notificador.NotificadorIncidentes;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//public class CalculadorDistanciaTests {
//
//    private Heladera heladeraA;
//    private Tecnico tecnicoA;
//    private Tecnico tecnicoB;
//    private ICalculadorDistanciaKM calculadorDistanciaKM;
//    private INotificadorIncidentes notificadorIncidentes;
//    private FallaTecnicaDTO dto;
//    private Usuario userA;
//    private Alerta alerta;
//    private FallaTecnica fallaTecnica;
//
//    @BeforeEach
//    public void setUp() {
//        calculadorDistanciaKM = new CalculadorDistanciaKM();
//
//        Documento dniA = new Documento(TipoDocumento.DNI, "44590721");
//        Cuil cuilA = new Cuil("22", dniA.getNumeroDeDocumento(), "5");
//        Ubicacion ubiA = new Ubicacion(-34.601323f, -58.602061f, new Calle("Matienzo", "s/n"));
//        AreaDeCobertura areaDeCobertura2 = new AreaDeCobertura(ubiA, TamanioArea.GRANDE);
//        areaDeCobertura2.setICalculadorDistanciaKM(calculadorDistanciaKM);
//        tecnicoA = new Tecnico("Bryan", "Battagliese", dniA, cuilA);
//        tecnicoA.setAreaDeCobertura(areaDeCobertura2);
//
//        Documento dniB = new Documento(TipoDocumento.DNI, "39056431");
//        Cuil cuilB = new Cuil("25", dniB.getNumeroDeDocumento(), "3");
//        Ubicacion ubiB = new Ubicacion(-34.608463f, -58.373456f, new Calle("Bolivar", "51"));
//        AreaDeCobertura areaDeCobertura = new AreaDeCobertura(ubiB, TamanioArea.GRANDE);
//        areaDeCobertura.setICalculadorDistanciaKM(calculadorDistanciaKM);
//        tecnicoB = new Tecnico("Max", "Verstappen", dniB, cuilB);
//        tecnicoB.setAreaDeCobertura(areaDeCobertura);
//
//        // Crear calculador de distancia y notificador de incidentes
//        notificadorIncidentes = new NotificadorIncidentes(List.of(tecnicoA, tecnicoB), calculadorDistanciaKM);
//
//        Ubicacion ubicacionA = new Ubicacion(-34.609806f, -58.391905f, new Calle("Av Entre Rios", "90"));
//
//        ModeloDeHeladera modelo = new ModeloDeHeladera("XMR-283");
//        heladeraA = new Heladera(modelo, "Congreso", ubicacionA);
//        modelo.setTemperaturaMaxima(2f);
//        modelo.setTemperaturaMinima(-2f);
//        SensorTemperatura sensorTemperaturaA = new SensorTemperatura(heladeraA);
//        heladeraA.setSensorTemperatura(sensorTemperaturaA);
//
//        userA = new Usuario("Jose", "****");
//
//        dto = new FallaTecnicaDTO();
//        dto.setNombreHeladera("Congreso");
//        dto.setNombreUsuario("Jose");
//        dto.setDescripcion("falla de temperatura bla bla bla");
//        dto.setFoto("null");
//
//        // Crear incidentes
//        alerta = IncidenteFactory.crearAlerta(heladeraA, "falla_temperatura");
//        fallaTecnica = IncidenteFactory.crearFallaTecnica(dto, heladeraA, userA);
//
//        alerta.setCalculadorDistanciaKM(calculadorDistanciaKM);
//        alerta.setNotificador(notificadorIncidentes);
//        fallaTecnica.setCalculadorDistanciaKM(calculadorDistanciaKM);
//        fallaTecnica.setNotificador(notificadorIncidentes);
//
//    }
//
//
//    @Test
//    public void tecnicoNotificadoEsElEsperado() {
//
//        //// Crear incidentes
//        //Alerta alerta = IncidenteFactory.crearAlerta(heladeraA,"falla_temperatura");
//        //FallaTecnica fallaTecnica = IncidenteFactory.crearFallaTecnica(dto,heladeraA,userA);
//
//        //alerta.setCalculadorDistanciaKM(calculadorDistanciaKM);
//        //alerta.setNotificador(notificadorIncidentes);
//        //fallaTecnica.setCalculadorDistanciaKM(calculadorDistanciaKM);
//        //fallaTecnica.setNotificador(notificadorIncidentes);
//
//        /// Verificar que se notifica al técnico B y no al técnico A
//        Tecnico tecnicoNotificado = notificadorIncidentes.tecnicoMasCercano(alerta); // Obtener el técnico notificado
//        assertEquals(tecnicoB, tecnicoNotificado, "Se esperaba que se notificara al técnico B");
//
//        tecnicoNotificado = notificadorIncidentes.tecnicoMasCercano(fallaTecnica); // Obtener el técnico notificado
//        assertEquals(tecnicoB, tecnicoNotificado, "Se esperaba que se notificara al técnico B");
//
//    }
//
//
//}
