package dev.iamforyy.taskmanagementapi.project.dto;

public record GetProjectStatsResponse(
        long totalTasks,
        long todo,
        long inProgress,
        long done,
        long completionPercentage
) {
}
