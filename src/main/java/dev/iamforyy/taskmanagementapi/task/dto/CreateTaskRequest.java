package dev.iamforyy.taskmanagementapi.task.dto;

import dev.iamforyy.taskmanagementapi.task.TaskPriority;
import dev.iamforyy.taskmanagementapi.task.TaskStatus;

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
