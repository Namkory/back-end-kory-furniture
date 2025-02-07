package kory.spring.com.bekoryfurniture.controller;

import jakarta.validation.Valid;
import kory.spring.com.bekoryfurniture.dto.request.CategoryRequest;
import kory.spring.com.bekoryfurniture.dto.response.ApiResponse;
import kory.spring.com.bekoryfurniture.dto.response.CategoryResponse;
import kory.spring.com.bekoryfurniture.exception.ErrorCode;
import kory.spring.com.bekoryfurniture.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    ApiResponse<CategoryResponse> createNewCategory(@RequestBody @Valid CategoryRequest request){
        CategoryResponse response = categoryService.createCategory(request);
        ApiResponse<CategoryResponse> res = new ApiResponse<>();
        res.setCode(ErrorCode.SUCCESSFUL.getCode());
        res.setMessage(ErrorCode.SUCCESSFUL.getMessage());
        res.setResult(response);
        return res;
    }

    @GetMapping()
    public ResponseEntity<Page<CategoryResponse>> getAllCategory(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size
    ) {
        Page<CategoryResponse> categoryResponsesPage = categoryService.getAllCategory(page, size);
        return new ResponseEntity<>(categoryResponsesPage, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    ApiResponse<?> deleteCategory(@PathVariable("id") Integer categoryId){
        categoryService.deleteCategory(categoryId);
        ApiResponse<?> response = new ApiResponse<>();
        response.setCode(200);
        response.setMessage("Delete category successful");

        return response;
    }
}
