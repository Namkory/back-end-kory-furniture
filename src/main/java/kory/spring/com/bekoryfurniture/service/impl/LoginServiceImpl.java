package kory.spring.com.bekoryfurniture.service.impl;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import kory.spring.com.bekoryfurniture.dto.request.ChangePasswordRequest;
import kory.spring.com.bekoryfurniture.dto.request.LoginRequest;
import kory.spring.com.bekoryfurniture.dto.request.LogoutRequest;
import kory.spring.com.bekoryfurniture.dto.request.introSpectRequest;
import kory.spring.com.bekoryfurniture.dto.response.LoginResponse;
import kory.spring.com.bekoryfurniture.dto.response.introSpectResponse;
import kory.spring.com.bekoryfurniture.entity.Admin;
import kory.spring.com.bekoryfurniture.entity.Customer;
import kory.spring.com.bekoryfurniture.entity.InvalidatedToken;
import kory.spring.com.bekoryfurniture.entity.Users;
import kory.spring.com.bekoryfurniture.exception.AppException;
import kory.spring.com.bekoryfurniture.exception.ErrorCode;
import kory.spring.com.bekoryfurniture.repository.InvalidatedTokenRepository;
import kory.spring.com.bekoryfurniture.repository.UserRepo;
import kory.spring.com.bekoryfurniture.service.LoginService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    @Value("${jwt.signerKey}")
    private String SIGNER_KEY;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private InvalidatedTokenRepository invalidatedTokenRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public LoginResponse handleLogin(LoginRequest request) {
        if(request.getUserName() == null || request.getPassword() == null){
            throw new AppException(ErrorCode.USERNAME_PASSWORD_INVALID);
        }
        var user = userRepo.findByUserName(request.getUserName())
                .orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXIST));

        if (user instanceof Admin admin) {
            if (admin.isDelete()) {
                throw new AppException(ErrorCode.ACCOUNT_DISABLED);
            }
        } else if (user instanceof Customer customer) {
            if (customer.isDelete()) {
                throw new AppException(ErrorCode.ACCOUNT_DISABLED);
            }
        } else {
            throw new AppException(ErrorCode.USER_TYPE_INVALID);
        }

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new AppException(ErrorCode.WRONG_PASSWORD);
        }

        var token = generateToken(user);

        LoginResponse response = new LoginResponse();
        response.setUserId(user.getId());
        response.setUserName(user.getUserName());
        response.setToken(token);
        response.setRoles(user.getRoles());

        return response;
    }

    @Override
    @Transactional
    public introSpectResponse introspect(introSpectRequest request) {
        var token = request.getToken();
        boolean isValid = true;
        try{
            verifyToken(token);
        } catch (AppException | JOSEException | ParseException e ) {
            isValid = false;
        }

        return introSpectResponse.builder()
                .valid(isValid)
                .build();
    }

    @Override
    @Transactional
    public SignedJWT verifyToken(String token) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);
        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        var verified = signedJWT.verify(verifier);

        if(!(verified && expiryTime.after(new Date()))){
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        if(invalidatedTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID())){
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        return signedJWT;
    }

    @Override
    @Transactional
    public void logout(LogoutRequest request) throws ParseException, JOSEException {
        var signToken = verifyToken(request.getToken());

        if(signToken == null){
            throw new AppException(ErrorCode.INVALID_TOKEN);
        }

        String jit = signToken.getJWTClaimsSet().getJWTID();
        Date expiryTime = signToken.getJWTClaimsSet().getExpirationTime();

        InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                .id(jit)
                .expiryTime(expiryTime)
                .build();

        invalidatedTokenRepository.save(invalidatedToken);
    }

    @Override
    @Transactional
    public void handleChangPassword(ChangePasswordRequest request) {
        var user = userRepo.findByUserName(request.getUserName())
                .orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXIST));
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        if(!bCryptPasswordEncoder.matches(request.getOldPassword(), user.getPassword())){
            throw new AppException(ErrorCode.WRONG_PASSWORD);
        }else {
            String hashedPassword = bCryptPasswordEncoder.encode(request.getNewPassword());
            user.setPassword(hashedPassword);
            userRepo.save(user);
        }
    }

    public String generateToken(Users user){
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUserName())
                .issuer("kory.com")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
                ))
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", buildScope(user))
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        try{
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e){
            log.info("Can not create token");
            throw new RuntimeException(e);
        }
    }

    private String buildScope(Users user){
        StringJoiner stringJoiner = new StringJoiner("");
        if(!CollectionUtils.isEmpty(user.getRoles())){
            user.getRoles().forEach(stringJoiner::add);
        }

        return stringJoiner.toString();
    }
}
