package dev.iamforyy.taskmanagementapi.task.dto;

import dev.iamforyy.taskmanagementapi.task.TaskStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateTaskStatusRequest(
        @NotNull
        TaskStatus status
) {
}
