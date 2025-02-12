package kory.spring.com.bekoryfurniture.service;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.SignedJWT;
import kory.spring.com.bekoryfurniture.dto.request.ChangePasswordRequest;
import kory.spring.com.bekoryfurniture.dto.request.LoginRequest;
import kory.spring.com.bekoryfurniture.dto.request.LogoutRequest;
import kory.spring.com.bekoryfurniture.dto.request.introSpectRequest;
import kory.spring.com.bekoryfurniture.dto.response.LoginResponse;
import kory.spring.com.bekoryfurniture.dto.response.introSpectResponse;

import java.text.ParseException;

public interface LoginService {

    LoginResponse handleLogin(LoginRequest request);

    introSpectResponse introspect(introSpectRequest request);

    SignedJWT verifyToken(String token) throws JOSEException, ParseException;

    void logout(LogoutRequest request) throws ParseException, JOSEException;

    void handleChangPassword(ChangePasswordRequest request);
}
