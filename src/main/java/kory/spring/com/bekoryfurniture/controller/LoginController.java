package kory.spring.com.bekoryfurniture.controller;

import com.nimbusds.jose.JOSEException;
import jakarta.validation.Valid;
import kory.spring.com.bekoryfurniture.dto.request.ChangePasswordRequest;
import kory.spring.com.bekoryfurniture.dto.request.LoginRequest;
import kory.spring.com.bekoryfurniture.dto.request.LogoutRequest;
import kory.spring.com.bekoryfurniture.dto.request.introSpectRequest;
import kory.spring.com.bekoryfurniture.dto.response.ApiResponse;
import kory.spring.com.bekoryfurniture.dto.response.LoginResponse;
import kory.spring.com.bekoryfurniture.dto.response.introSpectResponse;
import kory.spring.com.bekoryfurniture.exception.AppException;
import kory.spring.com.bekoryfurniture.exception.ErrorCode;
import kory.spring.com.bekoryfurniture.service.LoginService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LoginController {

    LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> handleLogin(@RequestBody @Valid LoginRequest request){
        LoginResponse result = loginService.handleLogin(request);
        result.setMessage("Login successful");
        return ResponseEntity.ok(result);
    }

    @PostMapping("/introspect")
    ApiResponse<introSpectResponse> authenticate(@RequestBody @Valid introSpectRequest request) throws ParseException, JOSEException {
        var result = loginService.introspect(request);

        if(!result.isValid()){
            throw new AppException(ErrorCode.INVALID_TOKEN);
        }

        return ApiResponse.<introSpectResponse>builder()
                .code(200)
                .message("Valid token")
                .result(result)
                .build();
    }

    @PostMapping("/logout")
    ApiResponse<Void> logout(@RequestBody @Valid LogoutRequest request) throws ParseException, JOSEException {
        loginService.logout(request);
        return ApiResponse.<Void>builder()
                .code(200)
                .message("Logout successful")
                .build();
    }

    @PostMapping("/change-password")
    public ApiResponse<?> handleChangePassword(@RequestBody @Valid ChangePasswordRequest request){
        loginService.handleChangPassword(request);
        ApiResponse<?> response = new ApiResponse<>();
        response.setCode(200);
        response.setMessage("Change password successful");

        return response;
    }

    @PostMapping("/forgot-password")
    public ApiResponse<?> handleForgotPassword(@RequestParam String email){
        loginService.forgotPassword(email, "Change password");
        ApiResponse<?> response = new ApiResponse<>();
        response.setCode(200);
        response.setMessage("Change password successful");
        return response;
    }
}
