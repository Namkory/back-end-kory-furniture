package kory.spring.com.bekoryfurniture.service.impl;

import kory.spring.com.bekoryfurniture.dto.ProductDto;
import kory.spring.com.bekoryfurniture.dto.UserDto;
import kory.spring.com.bekoryfurniture.dto.UserDtoRequest;
import kory.spring.com.bekoryfurniture.entity.Role;
import kory.spring.com.bekoryfurniture.entity.Users;
import kory.spring.com.bekoryfurniture.exception.ResourceNotFoundException;
import kory.spring.com.bekoryfurniture.repository.RoleRepo;
import kory.spring.com.bekoryfurniture.repository.UserRepo;
import kory.spring.com.bekoryfurniture.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private ModelMapper modelMapper;
    private UserRepo userRepo;
    private RoleRepo roleRepo;
    @Override
    public UserDto createNewUser(UserDtoRequest request) {
        Role role = roleRepo.findById(request.getRoleId())
                .orElseThrow(() -> new ResourceNotFoundException("Role khong tim thay"));

        Users userEntity = modelMapper.map(request, Users.class);
        userEntity.setRole(role);

        // Hash the password before saving to the database
//        BCryptPasswordEncoder hashPassword = new BCryptPasswordEncoder();
//        String hashedPassword = hashPassword.encode(request.getPassword());
//        userEntity.setPassword(hashedPassword);

        Users saveUser = userRepo.save(userEntity);
        UserDto response = modelMapper.map(saveUser, UserDto.class);
        return response;
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<Users> entityUsers = userRepo.findAll();
        List<UserDto> response = new ArrayList<>();
        for(int i = 0; i < entityUsers.size(); i++) {
            UserDto userDto = modelMapper.map(entityUsers.get(i), UserDto.class);
            userDto.setPassword("");
            response.add(userDto);
        }
        return response;
    }

    @Override
    public void deleteUser(Integer userId) {
        userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user khong ton tai"));
        userRepo.deleteById(userId);
    }

    @Override
    public UserDto getUserById(Integer userId) {
        Users userEntity = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user khong ton tai"));
        UserDto response = modelMapper.map(userEntity, UserDto.class);
        response.setPassword("");
        return response;
    }

    @Override
    public UserDto updateUser(UserDtoRequest request) {
        Users userEntity = userRepo.findById(request.getId()).orElseThrow(() ->
                new ResourceNotFoundException("user khong ton tai"));
        if(request.getPassword().isEmpty()){
            request.setPassword(userEntity.getPassword());
        }
        modelMapper.map(request, userEntity);
        Role userRole = roleRepo.findById(request.getRoleId()).get();
        userEntity.setRole(userRole);
        Users updateUser = userRepo.save(userEntity);
        UserDto response = modelMapper.map(updateUser, UserDto.class);
        return response;
    }
}
