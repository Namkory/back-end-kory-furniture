package kory.spring.com.bekoryfurniture.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerRequest {

    private int id;
    private String userName;
    private String password;

    @NotBlank(message = "name")
    private String name;

    @NotBlank(message = "image")
    private String image;

    @NotBlank(message = "email")
    private String email;

    @NotBlank(message = "dob")
    private String dob;

    @NotBlank(message = "gender")
    private String gender;

    @NotBlank(message = "address")
    private String address;

    @NotBlank(message = "phone")
    private String phone;

    @NotBlank(message = "paymentMethod")
    private String paymentMethod;
}
