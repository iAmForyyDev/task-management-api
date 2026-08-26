package dev.iamforyy.taskmanagementapi.task.dto;

import dev.iamforyy.taskmanagementapi.project.Project;
import dev.iamforyy.taskmanagementapi.task.TaskPriority;
import dev.iamforyy.taskmanagementapi.task.TaskStatus;
import dev.iamforyy.taskmanagementapi.user.User;

import java.time.Instant;

public record UpdateTaskResponse(
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
