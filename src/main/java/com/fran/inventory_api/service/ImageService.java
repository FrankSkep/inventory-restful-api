package com.fran.inventory_api.service;

import com.fran.inventory_api.entity.Image;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    Image uploadImage(MultipartFile file);

    Image update(MultipartFile file, Image image);

    void completeDeletion(Image image);

    void deleteFromCloudinary(String imageId);
}
