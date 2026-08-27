package dev.iamforyy.taskmanagementapi.task.dto;

import dev.iamforyy.taskmanagementapi.task.TaskPriority;
import dev.iamforyy.taskmanagementapi.task.TaskStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

public record CreateTaskRequest(

        @Schema(
                description = "Unique identifier of the task.",
                example = "1"
        )
        Long id,

        @Schema(
                description = "Title of the task.",
                example = "Implement authentication."
        )
        String title,

        @Schema(
                description = "Detailed description of the task.",
                example = "Implement JWT authentication using Spring Security."
        )
        String description,

        @Schema(
                description = "Current status of the task.",
                example = "IN_PROGRESS"
        )
        TaskStatus status,

        @Schema(
                description = "Current priority of the task.",
                example = "HIGH"
        )
        TaskPriority priority,

        @Schema(
                description = "Due date of the task.",
                example = "2026-09-15T18:00:00Z"
        )
        Instant dueDate,

        @Schema(
                description = "Date and time when the task was completed.",
                example = "2026-09-10T14:30:00Z"
        )
        Instant completedAt,

        @Schema(
                description = "Date and time when the task was created.",
                example = "2026-08-26T17:00:00Z"
        )
        Instant createdAt,

        @Schema(
                description = "Unique identifier of the User Assigned to this task.",
                example = "1"
        )
        Long userAssigneeId
) {
}
