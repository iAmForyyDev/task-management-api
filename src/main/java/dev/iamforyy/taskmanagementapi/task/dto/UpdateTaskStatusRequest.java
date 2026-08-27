package dev.iamforyy.taskmanagementapi.task.dto;

import dev.iamforyy.taskmanagementapi.task.TaskStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record UpdateTaskStatusRequest(
        @Schema(
                description = "New status of the task.",
                example = "DONE"
        )
        @NotNull
        TaskStatus status
) {
}
