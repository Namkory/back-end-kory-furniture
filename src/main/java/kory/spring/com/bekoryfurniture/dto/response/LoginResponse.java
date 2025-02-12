package kory.spring.com.bekoryfurniture.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class LoginResponse {

    private String message;
    private int userId;
    private String userName;
    private String token;
    private Set<String> roles;
}
