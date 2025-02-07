package kory.spring.com.bekoryfurniture.dto.response;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryResponse {

    private int id;
    private String name;
    private String createdAt;
}
