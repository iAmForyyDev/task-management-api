package dev.iamforyy.taskmanagementapi.project.dto.fetch;

import dev.iamforyy.taskmanagementapi.project.Project;
import dev.iamforyy.taskmanagementapi.task.dto.TaskResponse;
import dev.iamforyy.taskmanagementapi.user.dto.GetUserResponse;

import java.time.Instant;
import java.util.List;

public record GetProjectResponse(
        Long id,
        String name,
        String description,
        String color,
        Instant createdAt,
        GetUserResponse owner,
        List<TaskResponse> tasks
) {

    public static GetProjectResponse of(final Project project) {
        return new GetProjectResponse(
                project.id(),
                project.name(),
                project.description(),
                project.color(),
                project.createdAt(),
                GetUserResponse.of(project.owner()),
                project.tasks().stream()
                        .map(task -> new TaskResponse(
                                task.id(),
                                task.title(),
                                task.description(),
                                task.status(),
                                task.priority(),
                                task.dueDate(),
                                task.completedAt(),
                                task.createdAt(),
                                task.project(),
                                task.assignee()
                        ))
                        .toList()
        );
    }

}
