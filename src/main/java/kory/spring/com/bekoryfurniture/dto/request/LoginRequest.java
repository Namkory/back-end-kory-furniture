package kory.spring.com.bekoryfurniture.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

    @NotBlank(message = "USERNAME_REQUIRED")
    private String userName;

    @NotBlank(message = "PASSWORD_REQUIRED")
    private String password;

}
