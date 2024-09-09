package TemperaturasTest.RandomTemperaturasExample;

import domain.geografia.Calle;
import domain.geografia.Ubicacion;
import domain.heladera.Heladera.Heladera;
import domain.heladera.Heladera.ModeloDeHeladera;
import repositorios.repositoriosBDD.RepositorioHeladeras;
import domain.heladera.Sensores.SensorTemperatura;
import domain.incidentes.Incidente;
import domain.temperatura.VerificadorTemperaturas;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Random;

public class RandomVerificadorTemperaturas {
    public static void main(String[] args) {
        RepositorioHeladeras heladeras = new RepositorioHeladeras();

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

        heladeras.guardar(heladeraA);
        heladeras.guardar(heladeraB);
        heladeras.guardar(heladeraC);

        heladeraA.getIncidentes().clear();
        heladeraB.getIncidentes().clear();
        heladeraC.getIncidentes().clear();

        Random random = new Random();

        // Simular la recepción de temperaturas
        for (Heladera heladera : heladeras.obtenerTodasLasHeladeras()) {
            SensorTemperatura sensorTemperatura = heladera.getSensorTemperatura();

            float temperaturaAleatoria = (modelo.getTemperaturaMinima() - 5) + random.nextFloat() * ((modelo.getTemperaturaMaxima() + 5) - modelo.getTemperaturaMinima());

            // Convertir la temperatura a String y actualizar la heladera
            sensorTemperatura.recibirTemperaturaActual(String.valueOf(temperaturaAleatoria));
        }

        // Configurar y comenzar el verificador de conexión
        VerificadorTemperaturas verificador = new VerificadorTemperaturas(heladeras.obtenerTodasLasHeladeras());
        verificador.verificarTemperaturas(); // Ejecutar la verificación una vez

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("heladeras.txt"))) {
            for (Heladera heladera : heladeras.obtenerTodasLasHeladeras()) {
                writer.write("Nombre Identificador: " + heladera.getNombreIdentificador());
                writer.newLine();
                writer.write("Ubicacion: " + heladera.getUbicacion().getCalle().getNombre() + " " + heladera.getUbicacion().getCalle().getAltura());
                writer.newLine();
                writer.write("Modelo: " + heladera.getModelo().getNombreModelo());
                writer.newLine();
                writer.write("Temperatura Actual: " + heladera.getUltimaTemperaturaRegistrada());
                writer.newLine();
                writer.write("Fecha y Hora de la Temperatura Actual: " + heladera.ultimaTemperaturaRegistrada.getFechaYhora());
                writer.newLine();
                writer.newLine();
                writer.write("Incidentes:");
                writer.newLine();

                List<Incidente> incidentes = heladera.getIncidentes();
                if (!incidentes.isEmpty()) {
                    writer.write("Hay incidentes registrados.");
                    writer.newLine();

                } else {
                    writer.write("Heladera sin incidentes registrados.");
                    writer.newLine();
                }

                writer.newLine();
                writer.write(heladera.getEstadoHeladera().toString());
                writer.newLine();
                writer.write("-----------------------------------------------------");
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
