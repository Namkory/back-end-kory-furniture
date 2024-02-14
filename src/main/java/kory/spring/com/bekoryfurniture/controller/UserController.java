package kory.spring.com.bekoryfurniture.controller;

import kory.spring.com.bekoryfurniture.dto.ProductDto;
import kory.spring.com.bekoryfurniture.dto.UserDto;
import kory.spring.com.bekoryfurniture.dto.UserDtoRequest;
import kory.spring.com.bekoryfurniture.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping()
    public ResponseEntity<UserDto> creatNewUser(@RequestBody UserDtoRequest request){
        UserDto res = userService.createNewUser(request);
        return ResponseEntity.ok(res);
    }

    @GetMapping()
    ResponseEntity<List<UserDto>> getUsers() {
        List<UserDto> response = userService.getAllUsers();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") Integer userId){
        UserDto response = userService.getUserById(userId);
        return ResponseEntity.ok(response);
    }

    @PutMapping()
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDtoRequest request){
        UserDto response = userService.updateUser(request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Map<String, String>> deleteUser(@PathVariable("id") Integer userId) {
        userService.deleteUser(userId);
        Map<String, String> response = Map.of("status delete", "Delete success");
        return ResponseEntity.ok(response);
    }
}
