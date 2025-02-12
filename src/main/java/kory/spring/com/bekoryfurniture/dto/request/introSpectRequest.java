package kory.spring.com.bekoryfurniture.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class introSpectRequest {

    @NotBlank(message = "TOKEN_REQUIRED")
    String token;
}
