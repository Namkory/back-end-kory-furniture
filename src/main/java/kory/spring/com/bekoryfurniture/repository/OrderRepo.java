package kory.spring.com.bekoryfurniture.repository;

import kory.spring.com.bekoryfurniture.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<Orders, Integer> {
}
