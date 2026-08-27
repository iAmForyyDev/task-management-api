package dev.iamforyy.taskmanagementapi.project.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record GetProjectStatsResponse(

        @Schema(
                description = "Total tasks in the project.",
                example = "10"
        )
        long totalTasks,

        @Schema(
                description = "Total tasks in TODO status.",
                example = "4"
        )
        long todo,

        @Schema(
                description = "Total tasks in IN_PROGRESS status",
                example = "3"
        )
        long inProgress,

        @Schema(
                description = "Total tasks in COMPLETED status.",
                example = "3"
        )
        long done,

        @Schema(
                description = "The completion percentage of the completed tasks.",
                example = "30"
        )
        long completionPercentage
) {
}
