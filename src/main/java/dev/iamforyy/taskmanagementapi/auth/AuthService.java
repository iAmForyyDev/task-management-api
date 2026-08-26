package dev.iamforyy.taskmanagementapi.auth;

import dev.iamforyy.taskmanagementapi.auth.login.AuthLoginRequest;
import dev.iamforyy.taskmanagementapi.auth.login.AuthLoginResponse;
import dev.iamforyy.taskmanagementapi.auth.logout.AuthLogoutRequest;
import dev.iamforyy.taskmanagementapi.auth.refresh.AuthRefreshRequest;
import dev.iamforyy.taskmanagementapi.auth.refresh.AuthRefreshResponse;
import dev.iamforyy.taskmanagementapi.auth.register.AuthRegisterRequest;
import dev.iamforyy.taskmanagementapi.common.exception.ConflictException;
import dev.iamforyy.taskmanagementapi.common.exception.UnauthorizedException;
import dev.iamforyy.taskmanagementapi.common.response.MessageResponse;
import dev.iamforyy.taskmanagementapi.user.User;
import dev.iamforyy.taskmanagementapi.user.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final RefreshTokenService refreshTokenService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthService(
            final UserRepository userRepository,
            final RefreshTokenService refreshTokenService,
            final JwtService jwtService,
            final PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.refreshTokenService = refreshTokenService;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    public MessageResponse register(final AuthRegisterRequest registerRequest) {
        final String normalizedEmail = registerRequest.email().toLowerCase(Locale.ROOT);
        if (this.userRepository.findByEmail(normalizedEmail).isPresent()) {
            throw new ConflictException("Already exists an user with that email.");
        }

        final String normalizedUsername =  registerRequest.username().toLowerCase(Locale.ROOT);
        if (this.userRepository.findByUsername(normalizedUsername).isPresent()) {
            throw new ConflictException("Already exists an user with that username.");
        }

        final String passwordEncoded = this.passwordEncoder.encode(registerRequest.password());
        final User user = new User(
                registerRequest.username(),
                registerRequest.email(),
                passwordEncoded,
                registerRequest.userRole()
        );

        this.userRepository.save(user);
        return new MessageResponse("User registered.");
    }

    @Transactional
    public AuthLoginResponse login(final AuthLoginRequest loginRequest) {
        final User user = this.userRepository.findByEmail(loginRequest.email())
                .orElseThrow(() -> new UnauthorizedException("Invalid email."));

        if (!this.passwordEncoder.matches(loginRequest.password(), user.passwordHash())) {
            throw new UnauthorizedException("Invalid password.");
        }

        final String accessToken = this.jwtService.generateToken(user);
        final String refreshToken = this.refreshTokenService.issue(user);
        return new AuthLoginResponse(accessToken, refreshToken);
    }

    @Transactional
    public AuthRefreshResponse refresh(final AuthRefreshRequest refreshRequest) {
        final RefreshToken refreshToken = this.refreshTokenService.validate(refreshRequest.refreshToken());
        this.refreshTokenService.revoke(refreshToken);

        final User user = refreshToken.user();
        final String newAccessToken = this.jwtService.generateToken(user);
        final String newRefreshToken = this.refreshTokenService.issue(user);
        return new AuthRefreshResponse(newAccessToken, newRefreshToken);
    }

    public MessageResponse logout(final AuthLogoutRequest logoutRequest) {
        this.refreshTokenService.deleteByRawToken(logoutRequest.refreshToken());
        return new MessageResponse("Logged out.");
    }

}
