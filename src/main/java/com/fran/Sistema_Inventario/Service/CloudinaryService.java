package com.fran.Sistema_Inventario.Service;

import org.springframework.web.multipart.MultipartFile;

public interface CloudinaryService {

    public String uploadFile(MultipartFile file);
    public void deleteFile(String publicId);
}
