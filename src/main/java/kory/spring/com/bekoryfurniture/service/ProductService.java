package kory.spring.com.bekoryfurniture.service;

import kory.spring.com.bekoryfurniture.dto.ProductDto;
import kory.spring.com.bekoryfurniture.dto.ProductDtoRequest;
import kory.spring.com.bekoryfurniture.exception.ResourceNotFoundException;

import java.util.List;

public interface ProductService {
    ProductDto createNewProduct(ProductDtoRequest request) throws ResourceNotFoundException;
    List<ProductDto> getAll();
}
