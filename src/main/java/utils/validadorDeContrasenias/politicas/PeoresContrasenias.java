package utils.validadorDeContrasenias.politicas;

import domain.Config;

import java.io.*;

public class PeoresContrasenias implements IPoliticas {
    @Override
    public void chequearPolitica(String nombreUsuario, String contrasenia) throws Exception {
        String rutaArchivo = Config.getInstance().getProperty("peores_contrasenias_ruta");
        if (rutaArchivo == null || rutaArchivo.isEmpty()) {
            throw new Exception("No se configuró la ruta del archivo en config.properties");
        }

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(rutaArchivo)) {
            if (inputStream == null) {
                throw new Exception("No se pudo encontrar el archivo: " + rutaArchivo);
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.equals(contrasenia)) {
                    throw new Exception("La contraseña no es válida, pertenece a una de las peores 10.000 contraseñas");
                }
            }
        } catch (IOException e) {
            throw new Exception("Error al leer el archivo de contraseñas: " + e.getMessage());
        }
    }
}