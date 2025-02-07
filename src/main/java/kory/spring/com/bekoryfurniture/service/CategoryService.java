package kory.spring.com.bekoryfurniture.service;

import kory.spring.com.bekoryfurniture.dto.request.CategoryRequest;
import kory.spring.com.bekoryfurniture.dto.response.CategoryResponse;
import org.springframework.data.domain.Page;

public interface CategoryService {

    CategoryResponse createCategory(CategoryRequest request);

    Page<CategoryResponse> getAllCategory(int page, int size);

    void deleteCategory(Integer categoryId);
}
