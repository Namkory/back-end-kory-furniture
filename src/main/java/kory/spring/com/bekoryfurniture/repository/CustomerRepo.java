package kory.spring.com.bekoryfurniture.repository;

import kory.spring.com.bekoryfurniture.entity.Admin;
import kory.spring.com.bekoryfurniture.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepo extends JpaRepository<Customer, Integer> {

    boolean existsByUserName(String userName);

    Page<Customer> findByIsDeleteFalse(Pageable pageable);

    Optional<Customer> findByEmail(String email);
}
