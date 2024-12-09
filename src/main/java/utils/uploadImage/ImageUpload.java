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
    
    // Definimos las rutas para diferentes tipos de uploads
    private static final String VISITAS_DIR = "visitas";
    private static final String OFERTAS_DIR = "ofertas";
    
    public static String saveImage(UploadedFile file, String tipo) throws IOException {
        String uploadDir = BASE_DIR + File.separator + "uploads" + File.separator + tipo;
        String relativePath = "/uploads/" + tipo + "/";
        
        File uploadDirFile = new File(uploadDir);
        if (!uploadDirFile.exists()) {
            uploadDirFile.mkdirs();
        }

        String fileName = System.currentTimeMillis() + "_" + file.filename();
        String filePath = uploadDir + File.separator + fileName;

        try (InputStream input = file.content()) {
            Files.copy(input, Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
        }

        return relativePath + fileName;
    }
}