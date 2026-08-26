package dev.iamforyy.taskmanagementapi.auth.refresh;

public record AuthRefreshResponse(
        String accessToken,
        String refreshToken
) {
}
