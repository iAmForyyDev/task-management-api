package dev.mantiel.taskmanagementapi.project.dto;

import jakarta.validation.constraints.NotBlank;

public record ProjectRequest(
        @NotBlank
        String name,
        String description,
        String color
) {

}
