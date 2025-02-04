package com.fran.inventory_api.system.service;

import com.fran.inventory_api.system.entity.Image;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    Image uploadImage(MultipartFile file);

    Image updateImage(MultipartFile file, Image image);

    void completeDeletion(Image image);

    void deleteFromCloudinary(String imageId);
}
