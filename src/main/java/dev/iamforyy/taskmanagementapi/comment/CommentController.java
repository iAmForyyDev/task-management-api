package dev.iamforyy.taskmanagementapi.comment;

import dev.iamforyy.taskmanagementapi.comment.dto.UpdateCommentRequest;
import dev.iamforyy.taskmanagementapi.comment.dto.UpdateCommentResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;
    public CommentController(final CommentService commentService) {
        this.commentService = commentService;
    }

    @Operation(summary = "Update a comment by ID.")
    @PatchMapping("/{commentId}")
    public ResponseEntity<UpdateCommentResponse> updateCommentById(
            final @PathVariable Long commentId,
            final @Valid @RequestBody UpdateCommentRequest commentRequest,
            final Authentication authentication
    ) {
        return ResponseEntity.ok(this.commentService.updateCommentById(commentId, commentRequest, authentication));
    }

    @Operation(summary = "Delete a comment by ID.")
    @DeleteMapping("/{commentId}")
    public void deleteCommentById(final @PathVariable Long commentId, final Authentication authentication) {
        this.commentService.deleteCommentById(commentId, authentication);
    }

}
