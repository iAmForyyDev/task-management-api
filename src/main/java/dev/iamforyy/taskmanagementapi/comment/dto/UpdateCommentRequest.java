package dev.iamforyy.taskmanagementapi.comment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record UpdateCommentRequest(
        @Schema(
                description = "New content of the comment.",
                example = "This is an updated comment!"
        )
        @NotBlank
        String content
) {
}
