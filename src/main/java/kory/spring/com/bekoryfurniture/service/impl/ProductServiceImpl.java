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

import java.util.ArrayList;
import java.util.List;
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

    @Override
    public List<ProductDto> getAll() {
        List<Products> entityProducts = productRepo.findAll();

        List<ProductDto> response = new ArrayList<>();
        for(int i = 0; i < entityProducts.size(); i++) {
            ProductDto productDto = modelMapper.map(entityProducts.get(i), ProductDto.class);
            response.add(productDto);
        }
        return response;
    }

    @Override
    public ProductDto getById(Integer pId) {
        Products entity = productRepo.findById(pId).orElseThrow(() ->
                new ResourceNotFoundException("Product khong ton tai"));

        ProductDto response = modelMapper.map(entity, ProductDto.class);
        return response;
    }

    @Override
    public ProductDto updateProduct(ProductDtoRequest request) {
        Products entity = productRepo.findById(request.getId()).orElseThrow(() ->
                new ResourceNotFoundException("Product khong ton tai"));

        if(request.getImage() == null) {
            request.setThumbnail(entity.getThumbnail());
        }
        else {
            String urlImage = cloudinaryImageService.uploadImage(request.getImage());
            request.setThumbnail(urlImage);
        }

        // doan nay khi map cac field tu request qua entity thi truong category cua entity se null la
        // do request nhan cateId con entity no la mot doi tuong Cate
        modelMapper.map(request, entity);

        Category newCate = categoryRepo.findById(request.getCategoryId()).get();
        entity.setCategory(newCate);

        Products savedProduct = productRepo.save(entity);

        ProductDto res = modelMapper.map(savedProduct, ProductDto.class);

        return res;
    }

    @Override
    public void deleteProduct(Integer productId) {
        productRepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product khong ton tai"));
        productRepo.deleteById(productId);

    }
}
