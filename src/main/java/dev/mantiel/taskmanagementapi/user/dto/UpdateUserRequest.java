package dev.mantiel.taskmanagementapi.user.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateUserRequest(
        @NotBlank
        String username
) {
}
