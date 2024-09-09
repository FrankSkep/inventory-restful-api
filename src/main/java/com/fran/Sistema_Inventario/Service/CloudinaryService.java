package com.fran.Sistema_Inventario.Service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface CloudinaryService {

    public Map uploadFile(MultipartFile file) throws IOException;
    public void deleteFile(String publicId);
}
