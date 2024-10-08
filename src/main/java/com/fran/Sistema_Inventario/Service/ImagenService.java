package com.fran.Sistema_Inventario.Service;

import com.fran.Sistema_Inventario.Entity.Imagen;
import org.springframework.web.multipart.MultipartFile;

public interface ImagenService {

    Imagen uploadImage(MultipartFile file);

    public Imagen update(MultipartFile file, Imagen imagen);

    void completeDeletion(Imagen image);

    public void deleteFromCloudinary(String imageId);
}
