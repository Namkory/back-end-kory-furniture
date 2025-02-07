package kory.spring.com.bekoryfurniture.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShoppingCartDTO {

    private int id;

    @NotNull(message = "CUSTOMER_ID_REQUIRED")
    private int customerId;

    @NotNull(message = "PRODUCT_ID_REQUIRED")
    private int productId;

    private String createdAt;
}
