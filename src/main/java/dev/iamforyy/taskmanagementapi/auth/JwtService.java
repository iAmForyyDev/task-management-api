package dev.iamforyy.taskmanagementapi.auth;

import dev.iamforyy.taskmanagementapi.auth.config.JwtProperties;
import dev.iamforyy.taskmanagementapi.user.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {

    private final JwtProperties jwtProperties;
    private final SecretKey secretKey;

    public JwtService(final JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
        this.secretKey = Keys.hmacShaKeyFor(jwtProperties.secret().getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(final User user) {
        final Date now = new Date();
        final Date expiration = new Date(now.getTime() + this.jwtProperties.expiration());
        return Jwts.builder()
                .subject(user.id().toString())
                .claim("username", user.username())
                .issuedAt(now)
                .expiration(expiration)
                .signWith(this.secretKey)
                .compact();
    }

    public String extractSubject(final String token) {
        return Jwts.parser()
                .verifyWith(this.secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean isValid(final String token) {
        try {
            Jwts.parser()
                    .verifyWith(this.secretKey)
                    .build()
                    .parse(token);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

}
