package dev.iamforyy.taskmanagementapi.task.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

public record CreateCommentRequest(
        @Schema(
                description = "Content of the comment.",
                example = "This task is amazing!"
        )
        String content,

        @Schema(
                description = "Date and time when the comment was created.",
                example = "2026-08-26T17:00:00Z"
        )
        Instant createdAt,

        @Schema(
                description = "Unique identifier of the User Author.",
                example = "1"
        )
        Long authorUserId,

        @Schema(
                description = "Unique identifier of the Associated Task."
        )
        Long taskId
) {
}
