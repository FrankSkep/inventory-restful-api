package com.fran.InventoryAPI.Service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface CloudinaryService {

    Map upload(MultipartFile file) throws IOException;

    void delete(String publicId) throws IOException;
}
