package kory.spring.com.bekoryfurniture.controller;

import jakarta.validation.Valid;
import kory.spring.com.bekoryfurniture.dto.ShoppingCartDTO;
import kory.spring.com.bekoryfurniture.dto.response.ShoppingCartProduct;
import kory.spring.com.bekoryfurniture.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v2/shopping-cart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @PostMapping
    ResponseEntity<ShoppingCartDTO> createNewShoppingCart(@RequestBody @Valid ShoppingCartDTO request){
        ShoppingCartDTO response = shoppingCartService.createNewShoppingCartRecord(request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Map<String, String>> deleteShoppingCart(@PathVariable("id") Integer shoppingCartId){
        shoppingCartService.deleteShoppingCart(shoppingCartId);
        Map<String, String> res =Map.of("status delete", "Delete shopping cart success");
        return ResponseEntity.ok(res);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<ShoppingCartProduct>> getShoppingCartProductsByCustomerId(@PathVariable int customerId) {
        List<ShoppingCartProduct> shoppingCartProducts = shoppingCartService.listShoppingCartProductByCustomerId(customerId);
        if (shoppingCartProducts.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(shoppingCartProducts);
    }
}
