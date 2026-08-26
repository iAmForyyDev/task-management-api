package dev.mantiel.taskmanagementapi.project.dto;

public record ProjectTaskCounts(
        long total,
        long todo,
        long inProgress,
        long done
) {
}
