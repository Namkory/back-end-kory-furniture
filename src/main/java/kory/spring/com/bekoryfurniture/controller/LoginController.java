package kory.spring.com.bekoryfurniture.controller;

import com.nimbusds.jose.JOSEException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Auth Controller")
@Slf4j
public class LoginController {

    LoginService loginService;

    @Operation(summary = "Handle login", description = "The API requires a userName and password to handle authentication")
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> handleLogin(@RequestBody @Valid LoginRequest request){
        log.info("Request login, {}",
                request.toString());
        LoginResponse result = loginService.handleLogin(request);
        result.setMessage("Login successful");
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Check token", description = "The API requires a token to validate another token")
    @PostMapping("/introspect")
    ApiResponse<introSpectResponse> authenticate(@RequestBody @Valid introSpectRequest request) throws ParseException, JOSEException {
        log.info("Request token, {}",
                request.getToken());
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

    @Operation(summary = "Handle logout", description = "The API requires a token to validate and handle the logout process")
    @PostMapping("/logout")
    ApiResponse<Void> logout(@RequestBody @Valid LogoutRequest request) throws ParseException, JOSEException {
        log.info("Request token for logout, {}",
                request.getToken());
        loginService.logout(request);
        return ApiResponse.<Void>builder()
                .code(200)
                .message("Logout successful")
                .build();
    }

    @Operation(summary = "Handle change password", description = "Change password API")
    @PostMapping("/change-password")
    public ApiResponse<?> handleChangePassword(@RequestBody @Valid ChangePasswordRequest request){
        log.info("Request change password, {}",
                request.toString());
        loginService.handleChangPassword(request);
        ApiResponse<?> response = new ApiResponse<>();
        response.setCode(200);
        response.setMessage("Change password successful");

        return response;
    }

    @Operation(summary = "Handle password reset process", description = "The user provides an email, and the system will check whether the email exists. If it does, a new password will be reset and sent to the user via email.")
    @PostMapping("/forgot-password")
    public ApiResponse<?> handleForgotPassword(@RequestParam String email){
        log.info("Request reset password, {}",
                email);
        loginService.forgotPassword(email, "Change password");
        ApiResponse<?> response = new ApiResponse<>();
        response.setCode(200);
        response.setMessage("Change password successful");
        return response;
    }
}
