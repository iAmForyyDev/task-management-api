package dev.iamforyy.taskmanagementapi.auth;

import dev.iamforyy.taskmanagementapi.common.exception.UnauthorizedException;
import dev.iamforyy.taskmanagementapi.user.User;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.HexFormat;

@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    public RefreshTokenService(final RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    private String generateRefreshToken() {
        final byte[] bytes = new byte[32];
        final SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(bytes);
        return Base64
                .getEncoder()
                .withoutPadding()
                .encodeToString(bytes);
    }

    private String tokenHash(final String token) {
        try {
            final MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            final byte[] bytes = messageDigest.digest(token.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(bytes);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 algorithm not found", e);
        }
    }

    public String issue(final User user) {
        final String rawToken = this.generateRefreshToken();
        final RefreshToken refreshToken = new RefreshToken(
                this.tokenHash(rawToken),
                user,
                Instant.now().plus(30, ChronoUnit.DAYS)
        );

        this.refreshTokenRepository.save(refreshToken);
        return rawToken;
    }

    public RefreshToken validate(final String rawToken) {
        final RefreshToken refreshToken = this.refreshTokenRepository
                .findByTokenHash(this.tokenHash(rawToken))
                .orElseThrow(() -> new UnauthorizedException("Invalid refresh token."));

        if (refreshToken.revoked()) {
            throw new UnauthorizedException("Token is already revoked.");
        }

        if (refreshToken.isExpired()) {
            throw new UnauthorizedException("Token is expired.");
        }

        return refreshToken;
    }

    public void revoke(final RefreshToken refreshToken) {
        refreshToken.revoke();
    }

    public void deleteByRawToken(final String rawToken) {
        this.refreshTokenRepository.findByTokenHash(this.tokenHash(rawToken))
                .ifPresent(this.refreshTokenRepository::delete);
    }

}
