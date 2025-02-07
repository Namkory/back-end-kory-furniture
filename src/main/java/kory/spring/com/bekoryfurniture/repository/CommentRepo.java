package kory.spring.com.bekoryfurniture.repository;

import kory.spring.com.bekoryfurniture.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo extends JpaRepository<Comment, Integer> {
}
