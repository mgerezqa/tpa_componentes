package utils.zCronjobVerificadorTemperaturas;
import domain.geografia.Calle;
import domain.geografia.Ubicacion;
import domain.heladera.Heladera.Heladera;
import domain.heladera.Heladera.ModeloDeHeladera;
import domain.heladera.Sensores.SensorTemperatura;
import lombok.AllArgsConstructor;
import lombok.Data;
import utils.temperatura.VerificadorTemperaturas;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Data
public class TemperaturasSetUp {

    public List<Heladera> setUp(){
        Ubicacion ubicacionA = new Ubicacion(1234f, 6456f, new Calle("Av medrano", "6742"));
        Ubicacion ubicacionB = new Ubicacion(6542f, 5736f, new Calle("Av rivadavia", "13106"));
        Ubicacion ubicacionC = new Ubicacion(9053f, 1258f, new Calle("Juarez", "352"));

        ModeloDeHeladera modelo = new ModeloDeHeladera("XMR-283");
        modelo.setTemperaturaMaxima(22f);
        modelo.setTemperaturaMinima(-12f);

        Heladera heladeraA = new Heladera(modelo, "medrano", ubicacionA);
        Heladera heladeraB = new Heladera(modelo, "rivadavia", ubicacionB);
        Heladera heladeraC = new Heladera(modelo, "juarez", ubicacionC);

        SensorTemperatura sensorTemperaturaA = new SensorTemperatura(heladeraA);
        heladeraA.setSensorTemperatura(sensorTemperaturaA);
        SensorTemperatura sensorTemperaturaB = new SensorTemperatura(heladeraB);
        heladeraB.setSensorTemperatura(sensorTemperaturaB);
        SensorTemperatura sensorTemperaturaC = new SensorTemperatura(heladeraC);
        heladeraC.setSensorTemperatura(sensorTemperaturaC);

        List<Heladera> heladeras = new ArrayList<>();

        heladeras.add(heladeraA);
        heladeras.add(heladeraB);
        heladeras.add(heladeraC);

        // Lo hago fallar "a propósito" ...
        heladeraB.setUltimaTemperaturaRegistrada(6f, LocalDateTime.now().minusMinutes(8));
        heladeraA.setUltimaTemperaturaRegistrada(100f, LocalDateTime.now());

        return heladeras;
    }

    public void ejecucion(String filePath) {
        VerificadorTemperaturas verificadorTemperaturas = new VerificadorTemperaturas(setUp());
        verificadorTemperaturas.verificarTemperaturas();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {

            writer.write("Fecha y hora actual: " + LocalDateTime.now());
            writer.newLine();
            writer.newLine();

            if ((verificadorTemperaturas.getHeladerasConFallasDeTemperatura()).isEmpty()) {
                System.out.println("No hay heladeras con falla de temperatura ... ");
                writer.write("No hay heladeras con falla de temperatura ... ");
                writer.newLine();
            }
            if ((verificadorTemperaturas.getHeladerasConFallasDeConexion()).isEmpty()) {
                System.out.println("No hay heladeras con falla de conexion ... ");
                writer.write("No hay heladeras con falla de conexion ... ");
                writer.newLine();
            }
            for (Heladera heladera : (verificadorTemperaturas.getHeladerasConFallasDeTemperatura())) {
                System.out.println("Heladeras con falla temperatura: " + heladera.getNombreIdentificador() + '\n' +
                        "Temperatura registrada: " + heladera.ultimaTemperaturaRegistrada.getTemperatura());
                writer.write("Heladeras con falla temperatura: " + heladera.getNombreIdentificador() + '\n' +
                        "Temperatura registrada: " + heladera.ultimaTemperaturaRegistrada.getTemperatura());
                writer.newLine();
            }
            writer.newLine();
            for (Heladera heladera : (verificadorTemperaturas.getHeladerasConFallasDeConexion())) {
                System.out.println("Heladeras con falla de conexion: " + heladera.getNombreIdentificador() + '\n' +
                        "Horario ult temperatura: " + heladera.ultimaTemperaturaRegistrada.getFechaYhora());
                writer.write("Heladeras con falla de conexion: " + heladera.getNombreIdentificador() + '\n' +
                        "Horario ult temperatura: " + heladera.ultimaTemperaturaRegistrada.getFechaYhora());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
