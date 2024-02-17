package kory.spring.com.bekoryfurniture.service.impl;

import kory.spring.com.bekoryfurniture.dto.LoginRequest;
import kory.spring.com.bekoryfurniture.dto.LoginResponse;
import kory.spring.com.bekoryfurniture.entity.Users;
import kory.spring.com.bekoryfurniture.exception.ResourceNotFoundException;
import kory.spring.com.bekoryfurniture.repository.UserRepo;
import kory.spring.com.bekoryfurniture.service.AuthService;
import kory.spring.com.bekoryfurniture.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService  {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private JwtUtil jwtUtil;
    @Override
    public LoginResponse handleLogin(LoginRequest loginRequest) {
        Users user = userRepo.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Tai khoan nay khong ton tai!"));

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        if(!bCryptPasswordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Mat khau khong chinh xac!");
        }

        // create jwt khi xac thuc email va password thanh cong
        String token = jwtUtil.createJwt(user.getEmail());

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setJwt(token);
        loginResponse.setId(user.getId());
        return loginResponse;
    }
}
