package kory.spring.com.bekoryfurniture.repository;

import kory.spring.com.bekoryfurniture.entity.Admin;
import kory.spring.com.bekoryfurniture.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepo extends JpaRepository<Admin, Integer> {

    boolean existsByUserName(String userName);
}
