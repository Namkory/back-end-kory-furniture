package kory.spring.com.bekoryfurniture.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kory.spring.com.bekoryfurniture.dto.request.CategoryRequest;
import kory.spring.com.bekoryfurniture.dto.response.ApiResponse;
import kory.spring.com.bekoryfurniture.dto.response.CategoryResponse;
import kory.spring.com.bekoryfurniture.exception.ErrorCode;
import kory.spring.com.bekoryfurniture.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/category")
@Tag(name = "Category Controller")
@Slf4j
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Operation(summary = "Add category", description = "API create new category")
    @PostMapping
    ApiResponse<CategoryResponse> createNewCategory(@RequestBody @Valid CategoryRequest request){
        log.info("Request add category, {}",
                request.toString());
        CategoryResponse response = categoryService.createCategory(request);
        ApiResponse<CategoryResponse> res = new ApiResponse<>();
        res.setCode(ErrorCode.SUCCESSFUL.getCode());
        res.setMessage(ErrorCode.SUCCESSFUL.getMessage());
        res.setResult(response);
        return res;
    }

    @Operation(summary = "Get category list per page", description = "Return category by page and size")
    @GetMapping()
    public ResponseEntity<Page<CategoryResponse>> getAllCategory(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size
    ) {
        log.info("Request get all of categories");
        Page<CategoryResponse> categoryResponsesPage = categoryService.getAllCategory(page, size);
        return new ResponseEntity<>(categoryResponsesPage, HttpStatus.OK);
    }

    @Operation(summary = "Delete category", description = "API delete category")
    @DeleteMapping("/{id}")
    ApiResponse<?> deleteCategory(@PathVariable("id") Integer categoryId){
        log.info("Request delete category by categoryId={}", categoryId);
        categoryService.deleteCategory(categoryId);
        ApiResponse<?> response = new ApiResponse<>();
        response.setCode(200);
        response.setMessage("Delete category successful");

        return response;
    }
}
