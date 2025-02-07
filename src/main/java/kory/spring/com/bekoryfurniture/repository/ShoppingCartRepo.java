package kory.spring.com.bekoryfurniture.repository;

import kory.spring.com.bekoryfurniture.entity.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartRepo extends JpaRepository<ShoppingCart, Integer> {
}
