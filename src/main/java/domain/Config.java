package domain;

import java.io.FileReader;
import java.io.IOException;
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
        try (FileReader reader = new FileReader("src/main/resources/config.properties")) {
            properties.load(reader);
        } catch (IOException e) {
            throw new IOException("No se pudo cargar el archivo config.");
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}
