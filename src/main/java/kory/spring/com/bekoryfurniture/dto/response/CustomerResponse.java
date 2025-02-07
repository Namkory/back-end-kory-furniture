package kory.spring.com.bekoryfurniture.dto.response;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerResponse {

    private int id;
    private String userName;
    private String name;
    private String image;
    private String email;
    private String dob;
    private String gender;
    private String address;
    private String phone;
    private String paymentMethod;
    private String lastPurchaseDate;
    private String roles;
    private boolean isDelete;
    private String createdAt;
}
