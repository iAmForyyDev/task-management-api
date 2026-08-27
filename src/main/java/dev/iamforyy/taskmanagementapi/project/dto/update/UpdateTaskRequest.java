package dev.iamforyy.taskmanagementapi.project.dto.update;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record UpdateTaskRequest(

        @Schema(
                description = "New title for the task.",
                example = "Fix login bug,"
        )
        @NotBlank
        String title,

        @Schema(
                description = "New description for the task.",
                example = "Investigate and fix the 500 error on login."
        )
        String description
) {
}
