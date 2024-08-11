package com.fran.Sistema_Inventario.Service.Impl;

import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.firebase.cloud.StorageClient;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class FileUploadService {

    private final String BUCKET_NAME = "productos-inventario-7f2d7.appspot.com";

    public String uploadFile(MultipartFile file) throws IOException {
        // Genera un nombre único para la imagen
        String fileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();

        // Usa el cliente de Storage de Firebase
        Storage storage = StorageClient.getInstance().bucket().getStorage();

        // Información del blob (archivo) a subir
        BlobInfo blobInfo = BlobInfo.newBuilder(BUCKET_NAME, fileName)
                .setContentType(file.getContentType())
                .build();

        // Sube el archivo a Firebase Storage
        storage.create(blobInfo, file.getBytes());

        // Obtén la URL pública del archivo subido
        return "https://firebasestorage.googleapis.com/v0/b/"
                + BUCKET_NAME + "/o/"
                + fileName.replace("/", "%2F") + "?alt=media";
    }
}
