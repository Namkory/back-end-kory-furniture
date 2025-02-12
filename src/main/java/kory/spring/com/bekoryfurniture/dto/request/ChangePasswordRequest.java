package kory.spring.com.bekoryfurniture.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordRequest {

    @NotBlank(message = "USERNAME_REQUIRED")
    private String userName;

    @NotBlank(message = "OLD_PASSWORD_REQUIRED")
    private String oldPassword;

    @NotBlank(message = "NEW_PASSWORD_REQUIRED")
    private String newPassword;
}
