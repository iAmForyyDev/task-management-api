package dev.mantiel.taskmanagementapi.auth.login;

public record AuthLoginResponse(
        String accessToken,
        String refreshToken
) {
}
