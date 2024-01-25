package kory.spring.com.bekoryfurniture.dto;

import kory.spring.com.bekoryfurniture.entity.Category;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDto {
    private int id;
    private String name;
    private Double price;
    private String discount;
    private String description;
    private String thumbnail;
    private Category category;
}
