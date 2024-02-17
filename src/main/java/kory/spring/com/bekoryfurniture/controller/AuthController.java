package kory.spring.com.bekoryfurniture.controller;

import kory.spring.com.bekoryfurniture.dto.LoginRequest;
import kory.spring.com.bekoryfurniture.dto.LoginResponse;
import kory.spring.com.bekoryfurniture.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping
    public ResponseEntity<?> doLogin(@RequestBody LoginRequest loginRequest) {
        LoginResponse response = authService.handleLogin(loginRequest);
        return ResponseEntity.ok(response);
    }
}
