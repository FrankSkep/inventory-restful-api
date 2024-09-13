package com.fran.Sistema_Inventario.Service.Impl;

import com.fran.Sistema_Inventario.Entity.Imagen;
import com.fran.Sistema_Inventario.Repository.ImagenRepository;
import com.fran.Sistema_Inventario.Service.CloudinaryService;
import com.fran.Sistema_Inventario.Service.ImagenService;
import lombok.Builder;
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
    public Imagen uploadImage(MultipartFile file) throws IOException {
        Map uploadResult = cloudinaryService.upload(file);
        String imageUrl = (String) uploadResult.get("url");
        String imageId = (String) uploadResult.get("public_id");
        Imagen image = new Imagen(imageUrl, imageId);
        return imagenRepository.save(image);
    }

    @Override
    public void deleteImage(Imagen image) throws IOException {
        System.out.println("Imagen en deleteImage  : imagenservice" + image);
        cloudinaryService.delete(image.getImageId());
        imagenRepository.deleteById(image.getId());
    }
}
