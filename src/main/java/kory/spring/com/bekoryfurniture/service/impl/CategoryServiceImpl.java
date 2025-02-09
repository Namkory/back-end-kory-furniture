package kory.spring.com.bekoryfurniture.service.impl;

import kory.spring.com.bekoryfurniture.dto.request.CategoryRequest;
import kory.spring.com.bekoryfurniture.dto.response.CategoryResponse;
import kory.spring.com.bekoryfurniture.entity.Category;
import kory.spring.com.bekoryfurniture.enums.Role;
import kory.spring.com.bekoryfurniture.exception.AppException;
import kory.spring.com.bekoryfurniture.exception.ErrorCode;
import kory.spring.com.bekoryfurniture.repository.CategoryRepo;
import kory.spring.com.bekoryfurniture.service.CategoryService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static kory.spring.com.bekoryfurniture.utils.DateTimeUtils.getCurrentDate;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepo categoryRepo;
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public CategoryResponse createCategory(CategoryRequest request) {

        boolean exists = categoryRepo.existsByName(request.getName());
        if (exists){
            throw new AppException(ErrorCode.CATEGORY_NAME_EXISTED);
        }

        Category categoryEntity = new Category();
        categoryEntity.setName(request.getName());
        categoryEntity.setCreatedAt(getCurrentDate());
        categoryRepo.save(categoryEntity);

        return modelMapper.map(categoryEntity, CategoryResponse.class);
    }

    @Override
    @Transactional
    public Page<CategoryResponse> getAllCategory(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<Category> categoryPage = categoryRepo.findAll(pageable);

        Page<CategoryResponse> categoryResponsesPage = categoryPage.map(categoryEntity -> {
            CategoryResponse categoryResponse = modelMapper.map(categoryEntity, CategoryResponse.class);
            return categoryResponse;
        });

        return categoryResponsesPage;
    }

    @Override
    @Transactional
    public void deleteCategory(Integer categoryId) {
        categoryRepo.findById(categoryId).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND_CATEGORY));
        categoryRepo.deleteById(categoryId);

    }


}
