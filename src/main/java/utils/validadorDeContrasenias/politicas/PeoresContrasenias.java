package utils.validadorDeContrasenias.politicas;

import domain.Config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class PeoresContrasenias implements IPoliticas{
    @Override
    public void chequearPolitica(String nombreUsuario, String contrasenia) throws Exception {
        String rutaArchivo = Config.getInstance().getProperty("peores_contrasenias_ruta");
        if (rutaArchivo == null || rutaArchivo.isEmpty()) {
            throw new Exception("No se configuró la ruta del archivo en config.properties");
        }

        // Leer el archivo
        File file = new File(rutaArchivo);
        if (!file.exists()) {
            throw new Exception("El archivo especificado no existe: " + rutaArchivo);
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.equals(contrasenia)) {
                    throw new Exception("La contraseña no es válida, pertenece a una de las peores 10.000 contraseñas");
                }
            }
        }

        return;
    }
}
