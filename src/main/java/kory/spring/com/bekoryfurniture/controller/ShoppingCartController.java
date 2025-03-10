package kory.spring.com.bekoryfurniture.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kory.spring.com.bekoryfurniture.dto.ShoppingCartDTO;
import kory.spring.com.bekoryfurniture.dto.response.ShoppingCartProduct;
import kory.spring.com.bekoryfurniture.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v2/shopping-cart")
@Tag(name = "Shopping Controller")
@Slf4j
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Operation(summary = "Add shopping cart", description = "API create new shopping cart")
    @PostMapping
    ResponseEntity<ShoppingCartDTO> createNewShoppingCart(@RequestBody @Valid ShoppingCartDTO request){
        log.info("Request add shopping cart, {}",
                request.toString());
        ShoppingCartDTO response = shoppingCartService.createNewShoppingCartRecord(request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete shopping cart", description = "API delete shopping cart")
    @DeleteMapping("/{id}")
    ResponseEntity<Map<String, String>> deleteShoppingCart(@PathVariable("id") Integer shoppingCartId){
        log.info("Request delete shopping cart by shoppingCartId={}", shoppingCartId);
        shoppingCartService.deleteShoppingCart(shoppingCartId);
        Map<String, String> res =Map.of("status delete", "Delete shopping cart success");
        return ResponseEntity.ok(res);
    }

    @Operation(summary = "Get shopping cart list by customerId", description = "API shopping cart list by customerId")
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<ShoppingCartProduct>> getShoppingCartProductsByCustomerId(@PathVariable int customerId) {
        log.info("Request get shopping cart list by customerId={}", customerId);
        List<ShoppingCartProduct> shoppingCartProducts = shoppingCartService.listShoppingCartProductByCustomerId(customerId);
        if (shoppingCartProducts.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(shoppingCartProducts);
    }
}
