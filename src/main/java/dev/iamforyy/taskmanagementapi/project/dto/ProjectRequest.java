package dev.iamforyy.taskmanagementapi.project.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record ProjectRequest(

        @Schema(
                description = "Name of the project.",
                example = "My new project!"
        )
        @NotBlank
        String name,

        @Schema(
                description = "Optional description of the project.",
                example = "Tasks related to the Q3 marketing campaign"
        )
        String description,

        @Schema(
                description = "Hex color of the project.",
                example = "#4287f5"
        )
        String color
) {

}
