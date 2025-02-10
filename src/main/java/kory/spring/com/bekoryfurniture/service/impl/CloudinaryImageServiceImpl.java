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

    @Override
    public void deleteImage(String imageUrl) {
        try {
            String publicId = extractPublicId(imageUrl);
            if (publicId != null) {
                Map result = cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
                System.out.println("Deleted image: " + result);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error deleting image from Cloudinary", e);
        }
    }

    private String extractPublicId(String imageUrl) {
        try {
            String[] parts = imageUrl.split("/");
            String fileName = parts[parts.length - 1];
            return fileName.split("\\.")[0];
        } catch (Exception e) {
            return null;
        }
    }
}
