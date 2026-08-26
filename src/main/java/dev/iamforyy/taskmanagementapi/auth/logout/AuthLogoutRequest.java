package dev.iamforyy.taskmanagementapi.auth.logout;

import jakarta.validation.constraints.NotBlank;

public record AuthLogoutRequest(
        @NotBlank
        String refreshToken
) {
}
