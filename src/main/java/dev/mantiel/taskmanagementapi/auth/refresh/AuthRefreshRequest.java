package dev.mantiel.taskmanagementapi.auth.refresh;

import jakarta.validation.constraints.NotBlank;

public record AuthRefreshRequest(
        @NotBlank
        String refreshToken
) {
}
