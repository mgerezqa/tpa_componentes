package utils.uploadImage;

import io.javalin.http.UploadedFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class ImageUpload {
    private static final String JAR_DIR = new File(System.getProperty("user.dir")).getAbsolutePath();
    private static final String UPLOAD_DIR = JAR_DIR + "/uploads";
    private static final String IMG_DIR = UPLOAD_DIR + "/img";

    public static void initializeDirectories() {
        File uploadsDir = new File(UPLOAD_DIR);
        File imgDir = new File(IMG_DIR);
        
        if (!uploadsDir.exists()) {
            uploadsDir.mkdirs();
        }
        if (!imgDir.exists()) {
            imgDir.mkdirs();
        }
    }

    public static String saveImage(UploadedFile file, String tipo) throws IOException {
        String uploadDir = IMG_DIR + File.separator + tipo;
        String relativePath = "/img/" + tipo + "/";

        createDirectories(uploadDir);

        String fileName = System.currentTimeMillis() + "_" + file.filename();
        String filePath = uploadDir + File.separator + fileName;

        try (InputStream input = file.content()) {
            Files.copy(input, Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
        }

        return relativePath + fileName;
    }

    private static void createDirectories(String path) {
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    public static String getUploadsDirectory() {
        return UPLOAD_DIR;
    }
}