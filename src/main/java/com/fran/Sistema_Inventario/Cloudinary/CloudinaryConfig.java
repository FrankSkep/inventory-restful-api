package com.fran.Sistema_Inventario.Cloudinary;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

    private final Dotenv env = Dotenv.load();

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", env.get("CLOUDINARY_CLOUD_NAME"),
                "api_key", env.get("CLOUDINARY_API_KEY"),
                "api_secret", env.get("CLOUDINARY_API_SECRET")
        ));
    }
}
