package dev.iamforyy.taskmanagementapi.comment;

import dev.iamforyy.taskmanagementapi.comment.dto.UpdateCommentRequest;
import dev.iamforyy.taskmanagementapi.comment.dto.UpdateCommentResponse;
import dev.iamforyy.taskmanagementapi.common.exception.ForbiddenException;
import dev.iamforyy.taskmanagementapi.common.exception.NotFoundException;
import dev.iamforyy.taskmanagementapi.user.UserPrincipal;
import dev.iamforyy.taskmanagementapi.user.UserRole;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    public CommentService(final CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public UpdateCommentResponse updateCommentById(
            final Long commentId,
            final UpdateCommentRequest updateCommentRequest,
            final Authentication authentication
    ) {
        final UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        if (userPrincipal == null) {
            throw new NotFoundException("User not found.");
        }

        final Comment comment = this.commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Comment not found."));

        if (!userPrincipal.id().equals(comment.author().id())) {
            throw new ForbiddenException("User not author.");
        }

        final String commentContent = updateCommentRequest.content();
        if (updateCommentRequest.content() != null) {
            comment.content(commentContent);
        }

        this.commentRepository.save(comment);
        return new UpdateCommentResponse(commentContent);
    }

    public void deleteCommentById(final Long commentId, final Authentication authentication) {
        final Comment comment = this.commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Comment not found."));

        final UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        if (userPrincipal == null) {
            throw new NotFoundException("Yser not found.");
        }

        if (!comment.author().id().equals(userPrincipal.id()) || userPrincipal.userRole() != UserRole.ADMIN) {
            throw new ForbiddenException("You need to be the author or admin.");
        }

        this.commentRepository.deleteById(commentId);
    }

}
