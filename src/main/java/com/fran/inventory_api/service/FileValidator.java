package com.fran.inventory_api.service;

import java.util.Set;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

public class FileValidator {

    // Allowed file extensions
    private static final Set<String> ALLOWED_EXTENSIONS = Set.of("jpg", "jpeg", "png");

    // Allowed mime types
    private static final Set<String> ALLOWED_MIME_TYPES = Set.of("image/jpeg", "image/png", "image/jpg");

    // Max file size 5MB
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024;

    public static boolean isValidFile(MultipartFile file) {
        if (file == null) {
            return false;
        }

        if (file.isEmpty()) {
            return false;
        }

        String extension = getFileExtension(file);
        if (extension == null || !ALLOWED_EXTENSIONS.contains(extension.toLowerCase())) {
            return false;
        }

        String mimeType = file.getContentType();
        if (mimeType == null || !ALLOWED_MIME_TYPES.contains(mimeType)) {
            return false;
        }

        return file.getSize() <= MAX_FILE_SIZE;
    }

    private static String getFileExtension(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        if (StringUtils.hasText(originalFilename) && originalFilename.contains(".")) {
            return originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        }
        return null;
    }
}
