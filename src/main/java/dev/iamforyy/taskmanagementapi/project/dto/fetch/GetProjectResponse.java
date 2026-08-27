package dev.iamforyy.taskmanagementapi.project.dto.fetch;

import dev.iamforyy.taskmanagementapi.project.Project;
import dev.iamforyy.taskmanagementapi.task.dto.TaskResponse;
import dev.iamforyy.taskmanagementapi.user.dto.GetUserResponse;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;
import java.util.List;

public record GetProjectResponse(

        @Schema(
                description = "Unique identifier of the project.",
                example = "1"
        )
        Long id,

        @Schema(description = "Name of the project.")
        String name,

        @Schema(description = "Optional description of the project.")
        String description,

        @Schema(description = "Hex color of the project.")
        String color,

        @Schema(
                description = "Date and time when the project was created.",
                example = "2026-08-26T17:00:00Z"
        )
        Instant createdAt,

        @Schema(description = "User who created the project.")
        GetUserResponse owner,

        @Schema(description = "List of tasks created in the project.")
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
