package dev.mantiel.taskmanagementapi.project.dto.update;

import jakarta.validation.constraints.NotBlank;

public record UpdateTaskRequest(
        @NotBlank
        String title,
        String description
) {
}
