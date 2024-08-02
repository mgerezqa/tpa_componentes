package TemperaturasTest;
import utils.temperatura.VerificadorTemperaturas;
import domain.geografia.Calle;
import domain.geografia.Ubicacion;
import domain.heladera.Heladera.Heladera;
import domain.heladera.Heladera.ModeloDeHeladera;
import domain.heladera.Sensores.SensorTemperatura;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

public class TestVerificadorTemperatura {

    Ubicacion ubicacionA = new Ubicacion(1234f, 6456f, new Calle("Av medrano", "6742"));
    Ubicacion ubicacionB = new Ubicacion(6542f, 5736f, new Calle("Av rivadavia", "13106"));
    Ubicacion ubicacionC = new Ubicacion(9053f, 1258f, new Calle("Juarez", "352"));
    ModeloDeHeladera modelo = new ModeloDeHeladera("XMR-283");

    Heladera heladeraA = new Heladera(modelo, "medrano", ubicacionA);
    Heladera heladeraB = new Heladera(modelo, "rivadavia", ubicacionB);
    Heladera heladeraC = new Heladera(modelo, "juarez", ubicacionC);

    SensorTemperatura sensorTemperaturaA = new SensorTemperatura(heladeraA);
    SensorTemperatura sensorTemperaturaB = new SensorTemperatura(heladeraB);
    SensorTemperatura sensorTemperaturaC = new SensorTemperatura(heladeraC);

    List<Heladera> heladeras = new ArrayList<>();
    VerificadorTemperaturas verificadorTemperaturas = new VerificadorTemperaturas(heladeras);

    @BeforeEach
    public void init(){
        modelo.setTemperaturaMaxima(22f);
        modelo.setTemperaturaMinima(-12f);
        heladeraA.setSensorTemperatura(sensorTemperaturaA);
        heladeraB.setSensorTemperatura(sensorTemperaturaB);
        heladeraC.setSensorTemperatura(sensorTemperaturaC);

        heladeras.add(heladeraA);
        heladeras.add(heladeraB);
        heladeras.add(heladeraC);

        sensorTemperaturaA.recibirTemperaturaActual("-50");

        heladeraB.setUltimaTemperaturaRegistrada(6f, LocalDateTime.now());

        LocalDateTime haceSeisMinutos = LocalDateTime.now().minusMinutes(6);
        heladeraC.setUltimaTemperaturaRegistrada(17f, haceSeisMinutos);
    }

  @Test
  @DisplayName("La temperatura (-50), esta fuera del rango. Es una falla de temperatura")
  void falla_temperatura(){
      verificadorTemperaturas.verificarTemperaturas();
      Assertions.assertFalse(verificadorTemperaturas.getHeladerasConFallasDeTemperatura().isEmpty());
  }

  @Test
  @DisplayName("La ultima temperatura registrada data de hace mas de 5 minutos. Es una falla de conexion")
  void falla_conexion(){
      verificadorTemperaturas.verificarTemperaturas();
      Assertions.assertTrue(verificadorTemperaturas.getHeladerasConFallasDeConexion().contains(heladeraC));
  }

    @Test
    @DisplayName("No existen fallas en esta heladeraB")
    void sin_fallas(){
        heladeras.remove(heladeraC);
        heladeras.remove(heladeraA);
        verificadorTemperaturas.verificarTemperaturas();

        Assertions.assertTrue(verificadorTemperaturas.getHeladerasConFallasDeConexion().isEmpty() &&
                              verificadorTemperaturas.getHeladerasConFallasDeTemperatura().isEmpty());
  }

}
