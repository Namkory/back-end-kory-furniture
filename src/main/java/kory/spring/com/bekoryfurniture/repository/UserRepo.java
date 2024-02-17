package kory.spring.com.bekoryfurniture.repository;

import kory.spring.com.bekoryfurniture.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo  extends JpaRepository<Users, Integer> {
    Optional<Users> findByEmail(String email);
}
