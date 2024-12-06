package utils.uploadImage;

import io.javalin.http.UploadedFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class ImageUpload {
    // Directorio base de la aplicación
    private static final String BASE_DIR = System.getProperty("user.dir");
    // Directorio para uploads
    private static final String UPLOAD_DIR = BASE_DIR + File.separator + "uploads" + File.separator + "visitas";

    public static String saveImage(UploadedFile file) throws IOException {
        // Crear directorio si no existe
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        // Generar nombre único para el archivo
        String fileName = System.currentTimeMillis() + "_" + file.filename();
        String filePath = UPLOAD_DIR + File.separator + fileName;

        // Guardar archivo
        try (InputStream input = file.content()) {
            Files.copy(input, Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return filePath;
    }
}