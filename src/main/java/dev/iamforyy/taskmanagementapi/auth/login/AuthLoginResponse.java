package dev.iamforyy.taskmanagementapi.auth.login;

public record AuthLoginResponse(
        String accessToken,
        String refreshToken
) {
}
