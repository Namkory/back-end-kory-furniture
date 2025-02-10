package kory.spring.com.bekoryfurniture.service.impl;

import kory.spring.com.bekoryfurniture.dto.ShoppingCartDTO;
import kory.spring.com.bekoryfurniture.dto.response.ShoppingCartProduct;
import kory.spring.com.bekoryfurniture.entity.Products;
import kory.spring.com.bekoryfurniture.entity.ShoppingCart;
import kory.spring.com.bekoryfurniture.exception.AppException;
import kory.spring.com.bekoryfurniture.exception.ErrorCode;
import kory.spring.com.bekoryfurniture.repository.AdminRepo;
import kory.spring.com.bekoryfurniture.repository.ProductRepo;
import kory.spring.com.bekoryfurniture.repository.ShoppingCartRepo;
import kory.spring.com.bekoryfurniture.service.ShoppingCartService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static kory.spring.com.bekoryfurniture.utils.DateTimeUtils.getCurrentDate;

@Service
@AllArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private ShoppingCartRepo shoppingCartRepo;
    private ModelMapper modelMapper;
    private ProductRepo productRepo;

    @Override
    @Transactional
    public ShoppingCartDTO createNewShoppingCartRecord(ShoppingCartDTO request) {
        if(request.getCustomerId() == 0 || request.getProductId() == 0){
            throw new AppException(ErrorCode.INVALID_CUSTOMER_ID_OR_PRODUCT_ID);
        }
        ShoppingCart shoppingCartEntity = new ShoppingCart();
        shoppingCartEntity.setCustomerId(request.getCustomerId());
        shoppingCartEntity.setProductId(request.getProductId());
        shoppingCartEntity.setCreatedAt(getCurrentDate());
        shoppingCartRepo.save(shoppingCartEntity);

        return modelMapper.map(shoppingCartEntity, ShoppingCartDTO.class);
    }

    @Override
    @Transactional
    public void deleteShoppingCart(Integer shoppingCartId) {
        shoppingCartRepo.findById(shoppingCartId).orElseThrow(
                () -> new AppException(ErrorCode.NOT_FOUND_SHOPPING_CART));
        shoppingCartRepo.deleteById(shoppingCartId);
    }

    @Override
    @Transactional
    public List<ShoppingCartProduct> listShoppingCartProductByCustomerId(int customerId) {
        List<ShoppingCart> shoppingCarts = shoppingCartRepo.findByCustomerId(customerId);
        List<ShoppingCartProduct> shoppingCartProducts = new ArrayList<>();

        for (ShoppingCart cart : shoppingCarts) {
            int productId = cart.getProductId();
            Optional<Products> optionalProduct = productRepo.findById(productId);
            optionalProduct.ifPresent(product -> {
                // Tạo ShoppingCartProduct DTO
                ShoppingCartProduct shoppingCartProduct = new ShoppingCartProduct();
                shoppingCartProduct.setId(cart.getId());
                shoppingCartProduct.setName(product.getName());
                shoppingCartProduct.setProductId(product.getId());
                shoppingCartProduct.setSalePrice(product.getSalePrice());
                shoppingCartProduct.setEstablishedPrice(product.getEstablishedPrice());
                shoppingCartProduct.setThumbnailData(product.getThumbnailData());
                shoppingCartProduct.setSize(product.getSize());
                shoppingCartProducts.add(shoppingCartProduct);
            });

        }

        return shoppingCartProducts;
    }
}
