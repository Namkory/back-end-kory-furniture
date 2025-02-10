package kory.spring.com.bekoryfurniture.controller;

import jakarta.validation.Valid;
import kory.spring.com.bekoryfurniture.dto.request.AdminRequest;
import kory.spring.com.bekoryfurniture.dto.request.ProductsRequest;
import kory.spring.com.bekoryfurniture.dto.response.AdminResponse;
import kory.spring.com.bekoryfurniture.dto.response.ApiResponse;
import kory.spring.com.bekoryfurniture.dto.response.ProductsResponse;
import kory.spring.com.bekoryfurniture.exception.ErrorCode;
import kory.spring.com.bekoryfurniture.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    ApiResponse<ProductsResponse> createNewProduct(@ModelAttribute @Valid ProductsRequest request){
        ProductsResponse response = productService.createProduct(request);
        ApiResponse<ProductsResponse> res = new ApiResponse<>();
        res.setCode(ErrorCode.SUCCESSFUL.getCode());
        res.setMessage(ErrorCode.SUCCESSFUL.getMessage());
        res.setResult(response);
        return res;
    }

    @GetMapping()
    public ResponseEntity<Page<ProductsResponse>> getAllProduct(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size
    ) {
        Page<ProductsResponse> productResponsessPage = productService.getAllProduct(page, size);
        return new ResponseEntity<>(productResponsessPage, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    ApiResponse<ProductsResponse> getProductByID(@PathVariable("id") Integer productId) {
        ProductsResponse response = productService.getProductById(productId);
        ApiResponse<ProductsResponse> res = new ApiResponse<>();
        res.setCode(ErrorCode.GET_BY_ID_SUCCESSFUL.getCode());
        res.setMessage(ErrorCode.GET_BY_ID_SUCCESSFUL.getMessage());
        res.setResult(response);

        return res;
    }

    @PutMapping
    ApiResponse<ProductsResponse> updateNewProduct(@ModelAttribute @Valid ProductsRequest request){
        ProductsResponse response = productService.updateProduct(request);
        ApiResponse<ProductsResponse> res = new ApiResponse<>();
        res.setCode(ErrorCode.UPDATE_SUCCESSFUL.getCode());
        res.setMessage(ErrorCode.UPDATE_SUCCESSFUL.getMessage());
        res.setResult(response);
        return res;
    }

    @DeleteMapping("/{id}")
    ApiResponse<?> deleteProduct(@PathVariable("id") Integer productId){
        productService.deleteProduct(productId);
        ApiResponse<?> response = new ApiResponse<>();
        response.setCode(ErrorCode.DELETE_SUCCESSFUL.getCode());
        response.setMessage(ErrorCode.DELETE_SUCCESSFUL.getMessage());

        return response;
    }

    @GetMapping("/search")
    public List<ProductsResponse> searchProducts(@RequestParam String name) {
        return productService.searchProductsByName(name);
    }
}
