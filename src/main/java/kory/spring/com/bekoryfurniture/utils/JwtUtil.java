package kory.spring.com.bekoryfurniture.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expirationMs}")
    private int jwtExpirationMs;

    public String createJwt(String email) {
        Date now = new Date();
        Date expiration = new Date(
                now.getTime() + jwtExpirationMs
        );

        String jwtToken = Jwts.builder()
                .setSubject(email) // chu so huu
                .setIssuedAt(new Date()) // ngay tao
                .setExpiration(expiration) // thoi gian het han
                .signWith(key()) // chu ky jwt
                .compact();
        return jwtToken;
    }
    private Key key() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    public boolean validateJwt(String token) {
        try {
            // validate token thi se so sanh chu ky (scret) va thoi gian song cua token
            Jwts.parserBuilder().setSigningKey(key()).build().parse(token);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
