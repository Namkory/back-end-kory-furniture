package kory.spring.com.bekoryfurniture.repository;

import kory.spring.com.bekoryfurniture.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends JpaRepository<Products, Integer> {
}
