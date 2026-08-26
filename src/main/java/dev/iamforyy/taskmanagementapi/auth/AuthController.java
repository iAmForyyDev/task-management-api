package dev.iamforyy.taskmanagementapi.auth;

import dev.iamforyy.taskmanagementapi.auth.login.AuthLoginRequest;
import dev.iamforyy.taskmanagementapi.auth.login.AuthLoginResponse;
import dev.iamforyy.taskmanagementapi.auth.logout.AuthLogoutRequest;
import dev.iamforyy.taskmanagementapi.auth.refresh.AuthRefreshRequest;
import dev.iamforyy.taskmanagementapi.auth.refresh.AuthRefreshResponse;
import dev.iamforyy.taskmanagementapi.auth.register.AuthRegisterRequest;
import dev.iamforyy.taskmanagementapi.common.response.MessageResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    public AuthController(final AuthService authService) {
        this.authService = authService;
    }

    @GetMapping
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("pong");
    }

    @PostMapping("/register")
    public ResponseEntity<MessageResponse> register(final @Valid @RequestBody AuthRegisterRequest registerRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.authService.register(registerRequest));
    }

    @PostMapping("/login")
    public AuthLoginResponse login(final @Valid @RequestBody AuthLoginRequest loginRequest) {
        return this.authService.login(loginRequest);
    }

    @PostMapping("/refresh")
    public AuthRefreshResponse refresh(final @Valid @RequestBody AuthRefreshRequest refreshRequest) {
        return this.authService.refresh(refreshRequest);
    }

    @PostMapping("/logout")
    public MessageResponse logout(final @Valid @RequestBody AuthLogoutRequest logoutRequest) {
        return this.authService.logout(logoutRequest);
    }

}
