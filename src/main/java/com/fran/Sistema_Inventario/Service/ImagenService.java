package com.fran.Sistema_Inventario.Service;

import com.fran.Sistema_Inventario.Entity.Imagen;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImagenService {

    Imagen uploadImage(MultipartFile file) throws IOException;

    public Imagen actualizarImagen(MultipartFile file, Imagen imagen) throws IOException;

    void eliminarImagenCompleta(Imagen image) throws IOException;

    public void eliminarImagenCloudinary(String imageId) throws IOException;
}
