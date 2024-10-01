package com.fran.Sistema_Inventario.Service;

import com.fran.Sistema_Inventario.Entity.Imagen;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImagenService {

    Imagen uploadImage(MultipartFile file);

    public Imagen actualizarImagen(MultipartFile file, Imagen imagen);

    void eliminarImagenCompleta(Imagen image);

    public void eliminarImagenCloudinary(String imageId);
}
