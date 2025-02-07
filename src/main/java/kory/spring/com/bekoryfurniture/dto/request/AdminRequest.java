package kory.spring.com.bekoryfurniture.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminRequest {

    private int id;
    private String userName;
    private String password;

    @NotBlank(message = "name")
    private String name;

    @NotBlank(message = "image")
    private String image;

    @NotBlank(message = "dob")
    private String dob;

    @NotBlank(message = "address")
    private String address;

    @NotBlank(message = "gender")
    private String gender;

    @NotBlank(message = "phone")
    private String phone;
}
