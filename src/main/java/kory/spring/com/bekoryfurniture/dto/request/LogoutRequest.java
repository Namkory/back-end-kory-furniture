package kory.spring.com.bekoryfurniture.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LogoutRequest {

    @NotBlank(message = "TOKEN_REQUIRED")
    private String token;
}
