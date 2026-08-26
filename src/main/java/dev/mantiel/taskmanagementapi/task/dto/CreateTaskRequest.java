package dev.mantiel.taskmanagementapi.task.dto;

import dev.mantiel.taskmanagementapi.task.TaskPriority;
import dev.mantiel.taskmanagementapi.task.TaskStatus;

import java.time.Instant;

public record CreateTaskRequest(
        String title,
        String description,
        TaskStatus status,
        TaskPriority priority,
        Instant dueDate,
        Instant completedAt,
        Instant createdAt,
        Long userAssigneeId
) {
}
