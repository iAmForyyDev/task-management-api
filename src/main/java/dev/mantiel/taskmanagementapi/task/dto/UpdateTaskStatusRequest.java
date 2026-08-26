package dev.mantiel.taskmanagementapi.task.dto;

import dev.mantiel.taskmanagementapi.task.TaskStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateTaskStatusRequest(
        @NotNull
        TaskStatus status
) {
}
