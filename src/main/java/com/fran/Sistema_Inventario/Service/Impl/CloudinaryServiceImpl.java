package com.fran.Sistema_Inventario.Service.Impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.fran.Sistema_Inventario.Service.CloudinaryService;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryServiceImpl implements CloudinaryService {

    private final Cloudinary cloudinary;

    public CloudinaryServiceImpl() {
        Dotenv env = Dotenv.load();
        cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", env.get("CLOUDINARY_CLOUD_NAME"),
                "api_key", env.get("CLOUDINARY_API_KEY"),
                "api_secret", env.get("CLOUDINARY_API_SECRET")
        ));
    }

    @Override
    public Map upload(MultipartFile file) throws IOException {
        return cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
    }

    @Override
    public void delete(String publicId) throws IOException {
        cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
    }
}
