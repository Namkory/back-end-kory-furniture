package kory.spring.com.bekoryfurniture.repository;

import kory.spring.com.bekoryfurniture.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo  extends JpaRepository<Users, Integer> {
}
