package kory.spring.com.bekoryfurniture.service;

import kory.spring.com.bekoryfurniture.dto.request.AdminRequest;
import kory.spring.com.bekoryfurniture.dto.request.ProductsRequest;
import kory.spring.com.bekoryfurniture.dto.response.AdminResponse;
import kory.spring.com.bekoryfurniture.dto.response.ProductsResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {

    ProductsResponse createProduct(ProductsRequest request);

    Page<ProductsResponse> getAllProduct(int page, int size);

    ProductsResponse getProductById(Integer productId);

    ProductsResponse updateProduct(ProductsRequest request);

    void deleteProduct(Integer productId);

    List<ProductsResponse> searchProductsByName(String name);
}
