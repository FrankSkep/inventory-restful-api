package com.fran.Sistema_Inventario.Utils;

import java.util.Set;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

public class FileValidator {

    // Conjunto de extensiones permitidas
    private static final Set<String> ALLOWED_EXTENSIONS = Set.of("jpg", "jpeg", "png");

    // Conjunto de MIME's permitidos
    private static final Set<String> ALLOWED_MIME_TYPES = Set.of("image/jpeg", "image/png", "image/jpg");

    // Tamaño máximo permitido (por ejemplo, 5MB)
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024;

    public static boolean isValidFile(MultipartFile file) {
        // Verificar si el archivo está vacío
        if (file.isEmpty()) {
            return false;
        }
        // Verificar la extensión del archivo
        String extension = getFileExtension(file);
        if (extension == null || !ALLOWED_EXTENSIONS.contains(extension.toLowerCase())) {
            return false;
        }
        // Verificar el tipo MIME
        String mimeType = file.getContentType();
        if (mimeType == null || !ALLOWED_MIME_TYPES.contains(mimeType)) {
            return false;
        }

        return file.getSize() <= MAX_FILE_SIZE;
    }

    // Método para obtener la extensión del archivo
    private static String getFileExtension(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        if (StringUtils.hasText(originalFilename) && originalFilename.contains(".")) {
            return originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        }
        return null;
    }
}
