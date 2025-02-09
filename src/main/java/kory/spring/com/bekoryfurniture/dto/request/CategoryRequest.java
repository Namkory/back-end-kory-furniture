package kory.spring.com.bekoryfurniture.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryRequest {

    private int id;

    @NotBlank(message = "NAME_REQUIRED")
    private String name;
}
