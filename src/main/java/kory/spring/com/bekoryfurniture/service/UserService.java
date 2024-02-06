package kory.spring.com.bekoryfurniture.service;

import kory.spring.com.bekoryfurniture.dto.ProductDto;
import kory.spring.com.bekoryfurniture.dto.ProductDtoRequest;
import kory.spring.com.bekoryfurniture.dto.UserDto;
import kory.spring.com.bekoryfurniture.dto.UserDtoRequest;

import java.util.List;

public interface UserService {

    UserDto createNewUser(UserDtoRequest request);
    List<UserDto> getAllUsers();
    void deleteUser(Integer userId);
    UserDto getUserById(Integer userId);
    UserDto updateUser(UserDtoRequest request);
}
