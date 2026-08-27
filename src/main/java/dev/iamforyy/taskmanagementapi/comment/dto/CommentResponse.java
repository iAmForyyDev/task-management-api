package dev.iamforyy.taskmanagementapi.comment.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

public record CommentResponse(

        @Schema(
                description = "Unique identifier of the comment.",
                example = "1"
        )
        Long id,

        @Schema(
                description = "Content of the comment.",
                example = "This task is amazing!"
        )
        String content,

        @Schema(
                description = "Date and time when the comment was created.",
                example = "2026-09-10T14:30:00Z"
        )
        Instant createdAt,

        @Schema(
                description = "Unique identifier of the user who comments.",
                example = "1"
        )
        Long ownerUserId,

        @Schema(
                description = "Unique identifier of the Associated task.",
                example = "1"
        )
        Long taskId
) {
}
