package dev.iamforyy.taskmanagementapi.task.dto;

import java.time.Instant;

public record CreateCommentRequest(
        String content,
        Instant createdAt,
        Long authorUserId,
        Long taskId
) {
}
