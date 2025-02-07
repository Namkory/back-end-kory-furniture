package kory.spring.com.bekoryfurniture.repository;

import kory.spring.com.bekoryfurniture.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepo extends JpaRepository<Customer, Integer> {
}
