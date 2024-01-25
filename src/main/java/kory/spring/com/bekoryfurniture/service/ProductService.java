package kory.spring.com.bekoryfurniture.service;

import kory.spring.com.bekoryfurniture.dto.ProductDto;
import kory.spring.com.bekoryfurniture.dto.ProductDtoRequest;
import kory.spring.com.bekoryfurniture.exception.ResourceNotFoundException;

public interface ProductService {
    ProductDto createNewProduct(ProductDtoRequest request) throws ResourceNotFoundException;
}
