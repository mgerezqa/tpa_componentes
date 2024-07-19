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

    /*Configuración necesaria para Gmail */
    public static Properties getGmailProperties() {

        Properties prop = new Properties();
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        return prop;
    }
}
