package kory.spring.com.bekoryfurniture.controller;

import kory.spring.com.bekoryfurniture.dto.ProductDto;
import kory.spring.com.bekoryfurniture.dto.ProductDtoRequest;
import kory.spring.com.bekoryfurniture.service.ProductService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    ResponseEntity<List<ProductDto>> getProducts() {
        List<ProductDto> response = productService.getAll();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    ResponseEntity<ProductDto> getProduct(@PathVariable("id") Integer pId) {
        ProductDto response = productService.getById(pId);
        return ResponseEntity.ok(response);
    }


    @PostMapping
    ResponseEntity<ProductDto> createNewProduct(@ModelAttribute ProductDtoRequest request) {
        ProductDto res = productService.createNewProduct(request);
        return ResponseEntity.ok(res);
    }

    @PutMapping
    ResponseEntity<ProductDto> updateProduct(@ModelAttribute ProductDtoRequest request) {
        ProductDto res = productService.updateProduct(request);
        return ResponseEntity.ok(res);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Map<String, String>> deleteProduct(@PathVariable("id") Integer productId) {
        productService.deleteProduct(productId);
        Map<String, String> response = Map.of("status delete", "Delete success");
        return ResponseEntity.ok(response);
    }




}
