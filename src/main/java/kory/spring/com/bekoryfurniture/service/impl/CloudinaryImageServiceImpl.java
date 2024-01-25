package kory.spring.com.bekoryfurniture.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import kory.spring.com.bekoryfurniture.service.CloudinaryImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryImageServiceImpl implements CloudinaryImageService {

    @Autowired
    Cloudinary cloudinary;
    @Override
    public String uploadImage(MultipartFile file) {
        Map params = ObjectUtils.asMap(
                "folder", "products",
                "public_id", file.getOriginalFilename()
        );
        try {
            Map res = cloudinary.uploader().upload(file.getBytes(), params);
            Object url = res.get("url");
            return url.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
