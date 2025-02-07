package kory.spring.com.bekoryfurniture.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import kory.spring.com.bekoryfurniture.entity.OrderDetail;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderDTO {

    private int id;

    @NotNull(message = "CUSTOMER_ID_REQUIRED")
    private int customerId;

    private String customerName;

    @NotNull(message = "PRODUCTS_ID_REQUIRED")
    private int[] productsId;

    @NotBlank(message = "TOTAL_MONEY_REQUIRED")
    private String totalMoney;

    private String paymentId;

    private String status;

    @NotBlank(message = "PAYMENT_METHOD_REQUIRED")
    private String paymentMethod;

    private String orderDate;

    private List<OrderDetail> listOrderDetail;
}
