package kory.spring.com.bekoryfurniture.dto.request;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ProductsRequest {

    private int id;

    @NotBlank(message = "NAME_REQUIRED")
    private String name;

    @NotNull(message = "THUMBNAIL_REQUIRED")
    private MultipartFile[] thumbnailData;

    private String[] oldThumbnails;

    private String salePrice;

    @NotBlank(message = "ESTABLISHED_PRICE_REQUIRED")
    private String establishedPrice;

    @NotNull(message = "AMOUNT_REQUIRED")
    private int amount;

    @NotNull(message = "SIZE_REQUIRED")
    private String size;

    @NotNull(message = "DESCRIPTION_REQUIRED")
    private String description;

    private int categoryId;

}
