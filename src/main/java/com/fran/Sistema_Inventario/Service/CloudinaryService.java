package com.fran.Sistema_Inventario.Service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface CloudinaryService {

    public Map upload(MultipartFile file) throws IOException;

    public void delete(String publicId) throws IOException;
}
