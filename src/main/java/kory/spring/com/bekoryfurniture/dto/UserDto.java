package kory.spring.com.bekoryfurniture.dto;

import jakarta.persistence.*;
import kory.spring.com.bekoryfurniture.entity.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private int id;
    private String fullName;
    private String email;
    private String password;
    private String gender;
    private String phoneNumber;
    private String address;
    private Role role;
}
