package dev.iamforyy.taskmanagementapi.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record UpdateUserRequest(
        @Schema(
                description = "New username of the user.",
                example = "John_Doe"

        )
        @NotBlank
        String username
) {
}
