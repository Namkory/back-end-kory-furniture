package kory.spring.com.bekoryfurniture.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderProductsDto {

    private int id;
    private String image;
    private String name;
    private BigDecimal price;
    private String quantity;
    private BigDecimal totalMoney;
}
