package kory.spring.com.bekoryfurniture.service;

import kory.spring.com.bekoryfurniture.dto.ShoppingCartDTO;
import kory.spring.com.bekoryfurniture.dto.response.ShoppingCartProduct;

import java.util.List;

public interface ShoppingCartService {

    ShoppingCartDTO createNewShoppingCartRecord(ShoppingCartDTO request);

    void deleteShoppingCart(Integer shoppingCartId);

    List<ShoppingCartProduct> listShoppingCartProductByCustomerId(int customerId);
}
