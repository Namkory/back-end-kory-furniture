package kory.spring.com.bekoryfurniture.repository;

import kory.spring.com.bekoryfurniture.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepo extends JpaRepository<OrderDetail, Integer> {
}
