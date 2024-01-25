package kory.spring.com.bekoryfurniture.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDtoRequest extends ProductDto{
    private MultipartFile image;
    private int categoryId;

}
