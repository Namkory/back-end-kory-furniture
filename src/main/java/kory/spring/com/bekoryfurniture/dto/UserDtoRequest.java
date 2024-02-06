package kory.spring.com.bekoryfurniture.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDtoRequest extends UserDto{
    private int roleId;
}
