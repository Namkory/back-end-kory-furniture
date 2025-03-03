package kory.spring.com.bekoryfurniture.service.impl;

import kory.spring.com.bekoryfurniture.dto.request.ProductsRequest;
import kory.spring.com.bekoryfurniture.dto.response.ProductsResponse;
import kory.spring.com.bekoryfurniture.entity.Category;
import kory.spring.com.bekoryfurniture.entity.Products;
import kory.spring.com.bekoryfurniture.exception.AppException;
import kory.spring.com.bekoryfurniture.exception.ErrorCode;
import kory.spring.com.bekoryfurniture.repository.CategoryRepo;
import kory.spring.com.bekoryfurniture.repository.ProductRepo;
import kory.spring.com.bekoryfurniture.service.ProductService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

import static kory.spring.com.bekoryfurniture.utils.DateTimeUtils.getCurrentDate;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private ProductRepo productRepo;
    private ModelMapper modelMapper;
    private CategoryRepo categoryRepo;
    private CloudinaryImageServiceImpl cloudinaryImageService;

    @Override
    @Transactional
    public ProductsResponse createProduct(ProductsRequest request) {
        if (request.getCategoryId() == 0) {
            throw new AppException(ErrorCode.INVALID_CATEGORY_ID);
        }
        Category categoryEntity = categoryRepo.findById(request.getCategoryId()).orElseThrow(() ->
                new AppException(ErrorCode.NOT_FOUND_CATEGORY)
        );
        String[] thumbnailDataURL = new String[request.getThumbnailData().length];
        for (int i = 0; i < request.getThumbnailData().length; i++) {
            String urlImage = cloudinaryImageService.uploadImage(request.getThumbnailData()[i]);
            thumbnailDataURL[i] = urlImage;
        }
        Products productsEntity = new Products();
        modelMapper.map(request, productsEntity);
        productsEntity.setThumbnailData(thumbnailDataURL);
        productsEntity.setCategory(categoryEntity);
        productsEntity.setCreatedAt(getCurrentDate());
        productRepo.save(productsEntity);

        return modelMapper.map(productsEntity, ProductsResponse.class);
    }

    @Override
    @Transactional
    public Page<ProductsResponse> getAllProduct(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<Products> productsPage = productRepo.findAll(pageable);

        Page<ProductsResponse> productResponsesPage = productsPage.map(productEntity -> {
            ProductsResponse productResponse = modelMapper.map(productEntity, ProductsResponse.class);
            return productResponse;
        });

        return productResponsesPage;
    }

    @Override
    @Transactional
    public ProductsResponse getProductById(Integer productId) {
        Products productEntity = productRepo.findById(productId).orElseThrow(() ->
                new AppException(ErrorCode.NOT_FOUND_PRODUCT));

        return modelMapper.map(productEntity, ProductsResponse.class);
    }

    @Override
    @Transactional
    public ProductsResponse updateProduct(ProductsRequest request) {
        Products productEntity = productRepo.findById(request.getId())
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND_PRODUCT));
        productEntity.setName(request.getName());
        productEntity.setSalePrice(request.getSalePrice());
        productEntity.setEstablishedPrice(request.getEstablishedPrice());
        productEntity.setAmount(request.getAmount());
        productEntity.setSize(request.getSize());
        productEntity.setDescription(request.getDescription());
        Set<String> keepThumbnails = request.getOldThumbnails() != null
                ? new HashSet<>(Arrays.asList(request.getOldThumbnails()))
                : new HashSet<>();
        Set<String> currentThumbnails = productEntity.getThumbnailData() != null
                ? new HashSet<>(Arrays.asList(productEntity.getThumbnailData()))
                : new HashSet<>();
        List<String> updatedThumbnails = new ArrayList<>(keepThumbnails);
        for (String oldImage : currentThumbnails) {
            if (!keepThumbnails.contains(oldImage)) {
                cloudinaryImageService.deleteImage(oldImage);
            }
        }
        if (request.getThumbnailData() != null && request.getThumbnailData().length > 0) {
            for (MultipartFile file : request.getThumbnailData()) {
                String newImageUrl = cloudinaryImageService.uploadImage(file);
                updatedThumbnails.add(newImageUrl);
            }
        }
        productEntity.setThumbnailData(updatedThumbnails.toArray(new String[0]));
        productRepo.save(productEntity);

        return modelMapper.map(productEntity, ProductsResponse.class);
    }

    @Override
    @Transactional
    public void deleteProduct(Integer productId) {
        Products productEntity = productRepo.findById(productId).orElseThrow(() ->
                new AppException(ErrorCode.NOT_FOUND_PRODUCT));
        productRepo.delete(productEntity);
    }

    @Override
    @Transactional
    public List<ProductsResponse> searchProductsByName(String name) {
        List<Products> products = productRepo.findByNameContainingIgnoreCase(name);
        return products.stream()
                .map(product -> modelMapper.map(product, ProductsResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<ProductsResponse> findByCategoryId(int categoryId) {
        List<Products> listProduct = productRepo.findByCategoryId(categoryId);

        return listProduct.stream()
                .map(product -> modelMapper.map(product, ProductsResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<ProductsResponse> getAllProductNoPagination() {
        List<Products> listProductEntity = productRepo.findAll();
        return listProductEntity.stream()
                .map(product -> modelMapper.map(product, ProductsResponse.class))
                .collect(Collectors.toList());
    }
}
