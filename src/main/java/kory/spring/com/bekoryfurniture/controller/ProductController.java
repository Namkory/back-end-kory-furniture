package kory.spring.com.bekoryfurniture.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kory.spring.com.bekoryfurniture.dto.request.AdminRequest;
import kory.spring.com.bekoryfurniture.dto.request.ProductsRequest;
import kory.spring.com.bekoryfurniture.dto.response.AdminResponse;
import kory.spring.com.bekoryfurniture.dto.response.ApiResponse;
import kory.spring.com.bekoryfurniture.dto.response.ProductsResponse;
import kory.spring.com.bekoryfurniture.exception.ErrorCode;
import kory.spring.com.bekoryfurniture.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2/product")
@Tag(name = "Product Controller")
@Slf4j
public class ProductController {

    @Autowired
    private ProductService productService;

    @Operation(summary = "Add product", description = "API create new product")
    @PostMapping
    ApiResponse<ProductsResponse> createNewProduct(@ModelAttribute @Valid ProductsRequest request){
        log.info("Request add Product, {}",
                request.toString());
        ProductsResponse response = productService.createProduct(request);
        ApiResponse<ProductsResponse> res = new ApiResponse<>();
        res.setCode(ErrorCode.SUCCESSFUL.getCode());
        res.setMessage(ErrorCode.SUCCESSFUL.getMessage());
        res.setResult(response);
        return res;
    }

    @Operation(summary = "Get product list per page", description = "Return product by page and size")
    @GetMapping()
    public ResponseEntity<Page<ProductsResponse>> getAllProduct(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size
    ) {
        log.info("Request get all of products");
        Page<ProductsResponse> productResponsessPage = productService.getAllProduct(page, size);
        return new ResponseEntity<>(productResponsessPage, HttpStatus.OK);
    }

    @Operation(summary = "Get product detail", description = "API get product detail")
    @GetMapping("/{id}")
    ApiResponse<ProductsResponse> getProductByID(@PathVariable("id") Integer productId) {
        log.info("Request get detail product by productId={}", productId);
        ProductsResponse response = productService.getProductById(productId);
        ApiResponse<ProductsResponse> res = new ApiResponse<>();
        res.setCode(ErrorCode.GET_BY_ID_SUCCESSFUL.getCode());
        res.setMessage(ErrorCode.GET_BY_ID_SUCCESSFUL.getMessage());
        res.setResult(response);

        return res;
    }

    @Operation(summary = "Get product list by category", description = "API get product list by category")
    @GetMapping("/category/{categoryId}")
    public List<ProductsResponse> getProductsByCategory(@PathVariable int categoryId) {
        log.info("Request get product list by categoryId={}", categoryId);
        return productService.findByCategoryId(categoryId);
    }

    @Operation(summary = "Get product list no pagination", description = "API get product list no pagination")
    @GetMapping("/no-pagination")
    public List<ProductsResponse> getProductsNoPagination() {
        return productService.getAllProductNoPagination();
    }

    @Operation(summary = "Update product", description = "API update product")
    @PutMapping
    ApiResponse<ProductsResponse> updateNewProduct(@ModelAttribute @Valid ProductsRequest request){
        log.info("Request update product, {}", request.toString());
        ProductsResponse response = productService.updateProduct(request);
        ApiResponse<ProductsResponse> res = new ApiResponse<>();
        res.setCode(ErrorCode.UPDATE_SUCCESSFUL.getCode());
        res.setMessage(ErrorCode.UPDATE_SUCCESSFUL.getMessage());
        res.setResult(response);
        return res;
    }

    @Operation(summary = "Delete product", description = "API delete product")
    @DeleteMapping("/{id}")
    ApiResponse<?> deleteProduct(@PathVariable("id") Integer productId){
        log.info("Request delete product by productId={}", productId);
        productService.deleteProduct(productId);
        ApiResponse<?> response = new ApiResponse<>();
        response.setCode(ErrorCode.DELETE_SUCCESSFUL.getCode());
        response.setMessage(ErrorCode.DELETE_SUCCESSFUL.getMessage());

        return response;
    }

    @Operation(summary = "Get product list by name", description = "API to retrieve all products by name, ignoring case.")
    @GetMapping("/search")
    public List<ProductsResponse> searchProducts(@RequestParam String name) {
        log.info("Request get product list by name={}", name);
        return productService.searchProductsByName(name);
    }
}
