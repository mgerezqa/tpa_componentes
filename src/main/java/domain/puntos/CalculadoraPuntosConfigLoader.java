package domain.puntos;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class CalculadoraPuntosConfigLoader {
    private static Properties properties = new Properties();

    public static void init() throws IOException {
        try (FileReader reader = new FileReader("src/main/resources/coeficientes.properties")) {
            properties.load(reader);
        }
    }

    public static double getDoubleProperty(String key) {
        String value = properties.getProperty(key);
        if (value != null) {
            return Double.parseDouble(value);
        }
        throw new NumberFormatException("Property " + key + " is not a valid double.");
    }
}
