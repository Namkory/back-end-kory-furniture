package kory.spring.com.bekoryfurniture.repository;

import kory.spring.com.bekoryfurniture.entity.OrderDetail;
import kory.spring.com.bekoryfurniture.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepo extends JpaRepository<OrderDetail, Integer> {
}
