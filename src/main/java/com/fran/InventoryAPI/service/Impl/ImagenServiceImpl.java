package com.fran.InventoryAPI.service.Impl;

import com.fran.InventoryAPI.entity.Imagen;
import com.fran.InventoryAPI.repository.ImagenRepository;
import com.fran.InventoryAPI.service.CloudinaryService;
import com.fran.InventoryAPI.service.ImagenService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class ImagenServiceImpl implements ImagenService {

    private final CloudinaryService cloudinaryService;
    private final ImagenRepository imagenRepository;

    public ImagenServiceImpl(CloudinaryService cloudinaryService, ImagenRepository imageRepository) {
        this.cloudinaryService = cloudinaryService;
        this.imagenRepository = imageRepository;
    }

    @Override
    public Imagen uploadImage(MultipartFile file) {
        Map uploadResult = null;
        try {
            uploadResult = cloudinaryService.upload(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String imageUrl = (String) uploadResult.get("url");
        String imageId = (String) uploadResult.get("public_id");
        Imagen image = new Imagen(imageUrl, imageId);
        return imagenRepository.save(image);
    }

    // Actualiza la imagen de un producto
    @Override
    public Imagen update(MultipartFile file, Imagen imagen) {

        Imagen imageDB = imagenRepository.getReferenceById(imagen.getId());
        Map uploadResult = null;

        try {
            // Subir la nueva imagen
            uploadResult = cloudinaryService.upload(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (uploadResult != null) {
            // Obtener url e id de la nueva imagen
            String imageUrl = (String) uploadResult.get("url");
            String imageId = (String) uploadResult.get("public_id");
            // Actualizar url e id de imagen
            imageDB.setUrl(imageUrl);
            imageDB.setImageId(imageId);
        }
        return imagenRepository.save(imageDB);
    }

    // Elimina la imagen de cloudinary y de la base de datos
    @Override
    public void completeDeletion(Imagen image) {
        try {
            cloudinaryService.delete(image.getImageId());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        imagenRepository.deleteById(image.getId());
    }

    // Elimina la imagen de cloudinary
    @Override
    public void deleteFromCloudinary(String imageId) {
        try {
            cloudinaryService.delete(imageId);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
