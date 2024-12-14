package com.fran.inventory_api.service.Impl;

import com.fran.inventory_api.entity.Image;
import com.fran.inventory_api.exception.FileOperationErrorException;
import com.fran.inventory_api.repository.ImageRepository;
import com.fran.inventory_api.service.CloudinaryService;
import com.fran.inventory_api.service.ImageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class ImageServiceImpl implements ImageService {

    private final CloudinaryService cloudinaryService;
    private final ImageRepository imageRepository;

    public ImageServiceImpl(CloudinaryService cloudinaryService, ImageRepository imageRepository) {
        this.cloudinaryService = cloudinaryService;
        this.imageRepository = imageRepository;
    }

    @Override
    public Image uploadImage(MultipartFile file) {
        Map uploadResult = null;
        try {
            uploadResult = cloudinaryService.upload(file);
        } catch (
                IOException e) {
            throw new FileOperationErrorException(e.toString());
        }
        String imageUrl = (String) uploadResult.get("url");
        String imageId = (String) uploadResult.get("public_id");
        Image image = new Image(imageUrl, imageId);
        return imageRepository.save(image);
    }

    // Actualiza la imagen de un producto
    @Override
    public Image update(MultipartFile file, Image image) {

        Image imageDB = imageRepository.getReferenceById(image.getId());
        Map uploadResult = null;

        try {
            // Subir la nueva imagen
            uploadResult = cloudinaryService.upload(file);
        } catch (
                IOException e) {
            throw new FileOperationErrorException(e.toString());
        }

        if (uploadResult != null) {
            // Obtener url e id de la nueva imagen
            String imageUrl = (String) uploadResult.get("url");
            String imageId = (String) uploadResult.get("public_id");
            // Actualizar url e id de imagen
            imageDB.setUrl(imageUrl);
            imageDB.setImageId(imageId);
        }
        return imageRepository.save(imageDB);
    }

    // Elimina la imagen de cloudinary y de la base de datos
    @Override
    public void completeDeletion(Image image) {
        try {
            cloudinaryService.delete(image.getImageId());
        } catch (
                IOException e) {
            throw new FileOperationErrorException(e.toString());
        }
        imageRepository.deleteById(image.getId());
    }

    // Elimina la imagen de cloudinary
    @Override
    public void deleteFromCloudinary(String imageId) {
        try {
            cloudinaryService.delete(imageId);
        } catch (
                IOException e) {
            throw new FileOperationErrorException(e.toString());
        }
    }
}
