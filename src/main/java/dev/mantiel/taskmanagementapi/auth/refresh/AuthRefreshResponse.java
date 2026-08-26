package dev.mantiel.taskmanagementapi.auth.refresh;

public record AuthRefreshResponse(
        String accessToken,
        String refreshToken
) {
}
