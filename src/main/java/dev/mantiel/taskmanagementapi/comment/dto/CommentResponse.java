package dev.mantiel.taskmanagementapi.comment.dto;

import java.time.Instant;

public record CommentResponse(
        Long id,
        String content,
        Instant createdAt,
        Long ownerUserId,
        Long taskId
) {
}
