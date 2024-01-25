package kory.spring.com.bekoryfurniture.service;

import org.springframework.web.multipart.MultipartFile;

public interface CloudinaryImageService {
    String uploadImage(MultipartFile file);
}
