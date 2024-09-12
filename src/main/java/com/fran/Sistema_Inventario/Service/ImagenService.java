package com.fran.Sistema_Inventario.Service;

import com.fran.Sistema_Inventario.Entity.Imagen;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.IOException;

public interface ImagenService {
    Imagen uploadImage(MultipartFile file) throws IOException;

    void deleteImage(Imagen image) throws IOException;
}
