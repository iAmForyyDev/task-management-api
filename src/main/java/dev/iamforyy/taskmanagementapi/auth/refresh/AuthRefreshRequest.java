package dev.iamforyy.taskmanagementapi.auth.refresh;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record AuthRefreshRequest(
        @Schema(description = "Token used to obtain a new access token once it expired.")
        @NotBlank
        String refreshToken
) {
}
