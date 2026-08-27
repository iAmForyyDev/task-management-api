package dev.iamforyy.taskmanagementapi.auth.refresh;

import io.swagger.v3.oas.annotations.media.Schema;

public record AuthRefreshResponse(

        @Schema(
                description = "JWT access token used to authenticate subsequent requests.",
                example = "eyJhbGciOiJIUzI1NiJ9..."
        )
        String accessToken,

        @Schema(description = "Token used to obtain a new access token once it expired.")
        String refreshToken
) {
}
