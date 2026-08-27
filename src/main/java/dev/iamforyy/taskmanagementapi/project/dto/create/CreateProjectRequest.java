package dev.iamforyy.taskmanagementapi.project.dto.create;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record CreateProjectRequest(
        @Schema(
                description = "Name for the new project.",
                example = "My new project!",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank
        String name,

        @Schema(
                description = "Optional description for the new project.",
                example = "Tasks related to the Q3 marketing campaign",
                nullable = true,
                requiredMode = Schema.RequiredMode.NOT_REQUIRED
        )
        String description,

        @Schema(
                description = "Hex color code used to identify the project in the UI.",
                example = "#4287f5"
        )
        String color
) {
}
