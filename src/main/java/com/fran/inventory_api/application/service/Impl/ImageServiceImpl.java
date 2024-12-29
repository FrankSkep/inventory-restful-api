package com.fran.inventory_api.application.service.Impl;

import com.fran.inventory_api.application.entity.Image;
import com.fran.inventory_api.application.exception.FileOperationErrorException;
import com.fran.inventory_api.application.repository.ImageRepository;
import com.fran.inventory_api.application.service.CloudinaryService;
import com.fran.inventory_api.application.service.ImageService;
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

    @Override
    public Image updateImage(MultipartFile file, Image image) {

        Image imageDB = imageRepository.getReferenceById(image.getId());
        Map uploadResult = null;

        // try to upload the new image
        try {
            uploadResult = cloudinaryService.upload(file);
        } catch (
                IOException e) {
            throw new FileOperationErrorException(e.toString());
        }

        if (uploadResult != null) {
            String imageUrl = (String) uploadResult.get("url");
            String imageId = (String) uploadResult.get("public_id");
            imageDB.setUrl(imageUrl);
            imageDB.setImageId(imageId);
        }
        return imageRepository.save(imageDB);
    }

    @Override
    public void completeDeletion(Image image) {
        try {
            cloudinaryService.delete(image.getImageId());
        } catch (Exception e) {
            throw new FileOperationErrorException(e.toString());
        }
        imageRepository.deleteById(image.getId());
    }

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
