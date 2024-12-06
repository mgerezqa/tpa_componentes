package utils.uploadImage;

import io.javalin.http.UploadedFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class ImageUpload {
    private static final String BASE_DIR = System.getProperty("user.dir");
    private static final String UPLOAD_DIR = BASE_DIR + File.separator + "uploads" + File.separator + "visitas";
    private static final String RELATIVE_PATH = "/uploads/visitas/"; // Esta será la ruta URL para acceder a las imágenes

    public static String saveImage(UploadedFile file) throws IOException {
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        String fileName = System.currentTimeMillis() + "_" + file.filename();
        String filePath = UPLOAD_DIR + File.separator + fileName;

        try (InputStream input = file.content()) {
            Files.copy(input, Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
        }

        // Retorna la ruta relativa que se usará en el HTML
        return RELATIVE_PATH + fileName;
    }
}