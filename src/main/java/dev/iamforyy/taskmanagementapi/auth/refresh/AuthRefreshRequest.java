package dev.iamforyy.taskmanagementapi.auth.refresh;

import jakarta.validation.constraints.NotBlank;

public record AuthRefreshRequest(
        @NotBlank
        String refreshToken
) {
}
