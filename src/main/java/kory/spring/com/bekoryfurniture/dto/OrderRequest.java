package kory.spring.com.bekoryfurniture.dto;

import kory.spring.com.bekoryfurniture.entity.Products;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class OrderRequest {
    private int id;
    private String fullName;
    private String email;
    private String gender;
    private String address;
    private String phoneNumber;
    private String note;
    private String orderDate;
    private String status;
    private BigDecimal totalMoney;
    private ArrayList<OrderProductsDto> listProducts;
}
