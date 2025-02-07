package kory.spring.com.bekoryfurniture.dto.request;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductsRequest {

    private int id;

    @NotBlank(message = "NAME_REQUIRED")
    private String name;

    @NotNull(message = "THUMBNAIL_REQUIRED")
    private String[] thumbnailData;

    private String salePrice;

    @NotBlank(message = "ESTABLISHED_PRICE_REQUIRED")
    private String establishedPrice;

    @NotNull(message = "AMOUNT_REQUIRED")
    private int amount;

    @NotNull(message = "size")
    private String size;

    @NotNull(message = "description")
    private String description;

    private int categoryId;

}
