package com.fran.Sistema_Inventario.Service;

import com.fran.Sistema_Inventario.Entity.Imagen;
import org.springframework.web.multipart.MultipartFile;

public interface ImagenService {

    Imagen uploadImage(MultipartFile file);

    Imagen update(MultipartFile file, Imagen imagen);

    void completeDeletion(Imagen image);

    void deleteFromCloudinary(String imageId);
}
