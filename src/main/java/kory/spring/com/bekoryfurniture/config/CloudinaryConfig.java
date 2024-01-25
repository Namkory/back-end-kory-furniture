package kory.spring.com.bekoryfurniture.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

    @Bean
    Cloudinary cloudinary() {
        Cloudinary cloud = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "duv5ttmh4",
                "api_key", "247183693568289",
                "api_secret", "fDKuOQUv8eHxYxga515otmMU2lw"));
        return cloud;
    }
}
