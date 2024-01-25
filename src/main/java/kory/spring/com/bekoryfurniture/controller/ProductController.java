package kory.spring.com.bekoryfurniture.controller;

import kory.spring.com.bekoryfurniture.dto.ProductDto;
import kory.spring.com.bekoryfurniture.dto.ProductDtoRequest;
import kory.spring.com.bekoryfurniture.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    @Autowired
    private ProductService productService;
    @PostMapping
    ResponseEntity<ProductDto> createNewProduct(@ModelAttribute ProductDtoRequest request) {
        ProductDto res = productService.createNewProduct(request);
        return ResponseEntity.ok(res);
    }

    


}