package dev.mantiel.taskmanagementapi.project.dto.create;

import jakarta.validation.constraints.NotBlank;

public record CreateProjectRequest(
        @NotBlank
        String name,
        String description,
        String color
) {
}
