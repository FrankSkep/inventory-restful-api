package com.fran.inventory_api.service;

import com.fran.inventory_api.entity.Imagen;
import org.springframework.web.multipart.MultipartFile;

public interface ImagenService {

    Imagen uploadImage(MultipartFile file);

    Imagen update(MultipartFile file, Imagen imagen);

    void completeDeletion(Imagen image);

    void deleteFromCloudinary(String imageId);
}
