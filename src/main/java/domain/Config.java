package domain;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static Config instance;
    private Properties properties = new Properties();

    private Config() {
        try {
            init(); //Inicializa el archivo de configuración
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Config getInstance() {
        if (instance == null) {
            instance = new Config(); //Si no existe instancia de la clase, crea una nueva
        }
        return instance;
    }

    private void init() throws IOException { //Carga el archivo de configuración
        // Utiliza ClassLoader para cargar el archivo desde el classpath
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (inputStream == null) {
                throw new IOException("No se pudo cargar el archivo config.");
            }
            properties.load(inputStream); // Carga las propiedades desde el archivo
        } catch (IOException e) {
            throw new IOException("No se pudo cargar el archivo config.", e);
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public Long getPropertyToLong(String key) {
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
