package kory.spring.com.bekoryfurniture.repository;

import kory.spring.com.bekoryfurniture.entity.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShoppingCartRepo extends JpaRepository<ShoppingCart, Integer> {

    List<ShoppingCart> findByCustomerId(int customerId);
}
