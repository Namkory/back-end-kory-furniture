package kory.spring.com.bekoryfurniture.service;

import kory.spring.com.bekoryfurniture.dto.LoginRequest;
import kory.spring.com.bekoryfurniture.dto.LoginResponse;

public interface AuthService {
    LoginResponse handleLogin(LoginRequest loginRequest);
}
