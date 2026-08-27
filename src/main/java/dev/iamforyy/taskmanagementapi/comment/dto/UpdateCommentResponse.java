package dev.iamforyy.taskmanagementapi.comment.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record UpdateCommentResponse(
        @Schema(description = "New updated content of the comment.")
        String content
) {
}
