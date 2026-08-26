package dev.mantiel.taskmanagementapi.task.dto;

import dev.mantiel.taskmanagementapi.project.Project;
import dev.mantiel.taskmanagementapi.task.TaskPriority;
import dev.mantiel.taskmanagementapi.task.TaskStatus;
import dev.mantiel.taskmanagementapi.user.User;

import java.time.Instant;

public record TaskResponse(
        Long id,
        String title,
        String description,
        TaskStatus status,
        TaskPriority priority,
        Instant dueDate,
        Instant completedAt,
        Instant createdAt,
        Project project,
        User assignedUser
) {
}
