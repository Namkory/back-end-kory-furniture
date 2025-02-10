package kory.spring.com.bekoryfurniture.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StripeRequest {

    private int orderId;
    private Long amount;
    private String email;
}
