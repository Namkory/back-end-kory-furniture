package kory.spring.com.bekoryfurniture.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class AdminRequest {

    private int id;
    private String userName;
    private String password;

    @NotBlank(message = "NAME_REQUIRED")
    private String name;

    @NotNull(message = "IMAGE_REQUIRED")
    private MultipartFile image;

    @NotBlank(message = "DOB_REQUIRED")
    private String dob;

    @NotBlank(message = "ADDRESS_REQUIRED")
    private String address;

    @NotBlank(message = "GENDER_REQUIRED")
    private String gender;

    @NotBlank(message = "PHONE_REQUIRED")
    private String phone;
}
