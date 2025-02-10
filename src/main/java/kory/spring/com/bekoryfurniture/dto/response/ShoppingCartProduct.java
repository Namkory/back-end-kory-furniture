package kory.spring.com.bekoryfurniture.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShoppingCartProduct {

    private int id;
    private String name;
    private int productId;
    private String[] thumbnailData;
    private String salePrice;
    private String establishedPrice;
    private String size;
}
