package kory.spring.com.bekoryfurniture.dto.response;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import kory.spring.com.bekoryfurniture.dto.CommentDTO;
import kory.spring.com.bekoryfurniture.entity.Category;
import kory.spring.com.bekoryfurniture.entity.Comment;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
public class ProductsResponse {

    private int id;
    private String name;
    private String[] thumbnailData;
    private String salePrice;
    private String establishedPrice;
    private int amount;
    private String size;
    private String description;
    private String createdAt;
    private CategoryResponse category;
    private List<CommentDTO> listComment;
}
