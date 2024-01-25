package kory.spring.com.bekoryfurniture.service.impl;

import kory.spring.com.bekoryfurniture.dto.ProductDto;
import kory.spring.com.bekoryfurniture.dto.ProductDtoRequest;
import kory.spring.com.bekoryfurniture.entity.Category;
import kory.spring.com.bekoryfurniture.entity.Products;
import kory.spring.com.bekoryfurniture.exception.ResourceNotFoundException;
import kory.spring.com.bekoryfurniture.repository.CategoryRepo;
import kory.spring.com.bekoryfurniture.repository.ProductRepo;
import kory.spring.com.bekoryfurniture.service.CloudinaryImageService;
import kory.spring.com.bekoryfurniture.service.ProductService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private ProductRepo productRepo;
    private CategoryRepo categoryRepo;
    private ModelMapper modelMapper;
    private CloudinaryImageService cloudinaryImageService;
    @Override
    public ProductDto createNewProduct(ProductDtoRequest request)  {
//        // categoryRepo.findById(request.getCategoryId()) -> Tra ve mot optional cua category
//        Optional<Category> optionCate = categoryRepo.findById(request.getCategoryId());
//        if(optionCate.isPresent()) {
//            String urlImage = cloudinaryImageService.uploadImage(request.getImage());
//
//            Products productsEntity = modelMapper.map(request, Products.class);
//            productsEntity.setThumbnail(urlImage);
//            productsEntity.setCategory(optionCate.get());
//
//            Products savedProduct = productRepo.save(productsEntity);
//
//            ProductDto response = modelMapper.map(savedProduct, ProductDto.class);
//            return response;
//        }
//        else {
//            throw new ResourceNotFoundException("Category khong tim thay!");
//        }

        // categoryRepo.findById(request.getCategoryId()) -> Tra ve mot optional cua category
        Category cate = categoryRepo.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category khong tim thay"));

        String urlImage = cloudinaryImageService.uploadImage(request.getImage());

        Products productsEntity = modelMapper.map(request, Products.class);
        productsEntity.setThumbnail(urlImage);
        productsEntity.setCategory(cate);

        Products savedProduct = productRepo.save(productsEntity);

        ProductDto response = modelMapper.map(savedProduct, ProductDto.class);
        return response;
    }
}
