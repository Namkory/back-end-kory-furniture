package kory.spring.com.bekoryfurniture.config;

import com.nimbusds.jose.JOSEException;
import kory.spring.com.bekoryfurniture.dto.request.introSpectRequest;
import kory.spring.com.bekoryfurniture.exception.AppException;
import kory.spring.com.bekoryfurniture.exception.ErrorCode;
import kory.spring.com.bekoryfurniture.exception.ResourceNotFoundException;
import kory.spring.com.bekoryfurniture.service.LoginService;
import kory.spring.com.bekoryfurniture.service.impl.LoginServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.text.ParseException;
import java.util.Objects;

@Component
public class CustomerJwtDecoder implements JwtDecoder {

    @Value("${jwt.signerKey}")
    private String signerKey;

    @Autowired
    private LoginService loginService;

    private NimbusJwtDecoder nimbusJwtDecoder = null;

    @Override
    public Jwt decode(String token) throws ResourceNotFoundException {

        var response = loginService.introspect(introSpectRequest.builder().token(token).build());

        if(!response.isValid()){
            throw new ResourceNotFoundException("Token invalid");
        }

        if (Objects.isNull(nimbusJwtDecoder)){
            SecretKeySpec secretKeySpec = new SecretKeySpec(signerKey.getBytes(), "HS512");
            nimbusJwtDecoder = NimbusJwtDecoder
                    .withSecretKey(secretKeySpec)
                    .macAlgorithm(MacAlgorithm.HS512)
                    .build();

        }

        return nimbusJwtDecoder.decode(token);
    }

}
