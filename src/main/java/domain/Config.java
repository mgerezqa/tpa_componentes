package domain;

import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

public class Config {
    private static Properties properties = new Properties();

    public static void init() throws IOException {
        try (FileReader reader = new FileReader("src/main/resources/config.properties")) {
            properties.load(reader);
        }catch (IOException e) {
            throw new IOException("No se pudo cargar el archivo config.");
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static Long getPropertyToLong(String key) {
        String property = properties.getProperty(key);
        if (property != null) {
            try {
                return Long.parseLong(property);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException();
            }
        }
        throw new IllegalArgumentException();
    }
}

