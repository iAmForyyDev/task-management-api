package dev.iamforyy.taskmanagementapi.task;

import dev.iamforyy.taskmanagementapi.comment.Comment;
import dev.iamforyy.taskmanagementapi.comment.CommentRepository;
import dev.iamforyy.taskmanagementapi.comment.dto.CommentResponse;
import dev.iamforyy.taskmanagementapi.common.exception.NotFoundException;
import dev.iamforyy.taskmanagementapi.project.dto.update.UpdateTaskRequest;
import dev.iamforyy.taskmanagementapi.task.dto.CreateCommentRequest;
import dev.iamforyy.taskmanagementapi.task.dto.TaskResponse;
import dev.iamforyy.taskmanagementapi.task.dto.UpdateTaskStatusRequest;
import dev.iamforyy.taskmanagementapi.user.User;
import dev.iamforyy.taskmanagementapi.user.UserRepository;
import org.springframework.data.jpa.domain.UpdateSpecification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final CommentRepository commentRepository;

    public TaskService(
            final UserRepository userRepository,
            final TaskRepository taskRepository,
            final CommentRepository commentRepository
    ) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
        this.commentRepository = commentRepository;
    }

    public TaskResponse fetchTaskById(final Long taskId) {
        final Task task = this.taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException("Task not found!"));

        return new TaskResponse(
                taskId,
                task.title(),
                task.description(),
                task.status(),
                task.priority(),
                task.dueDate(),
                task.completedAt(),
                task.createdAt(),
                task.project(),
                task.assignee()
        );
    }

    public String updateTaskStatusById(
            final Long taskId,
            final UpdateTaskStatusRequest statusRequest
    ) {
        final UpdateSpecification<Task> updateStatus = UpdateSpecification
                .<Task>update((root, update, _) -> update.set(root.get("status"), statusRequest.status()))
                .where(TaskSpecifications.updateByTaskId(taskId));

        final long updateRows = this.taskRepository.update(updateStatus);
        if (updateRows == 0) {
            throw new NotFoundException("Task not found!");
        }

        return "Task Status updated successfully!";
    }

    public String updateTaskById(final Long taskId, final UpdateTaskRequest taskRequest) {
        final Task task = this.taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException("Task not found"));

        if (taskRequest.title() != null && !taskRequest.title().isBlank()) {
            task.title(taskRequest.title());
        }

        if (taskRequest.description() != null) {
            task.description(taskRequest.description());
        }

        this.taskRepository.save(task);
        return "done";
    }

    public void deleteTaskById(final Long taskId) {
        final Task task = this.taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException("Task not found"));

        this.taskRepository.delete(task);
    }

    public void createCommentByTaskId(
            final Long taskId,
            final CreateCommentRequest commentRequest
    ) {

        final Task task = this.taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException("Task not found"));

        final User user = this.userRepository.findById(commentRequest.authorUserId())
                .orElseThrow(() -> new NotFoundException("User not found"));

        final Comment comment = new Comment(
                commentRequest.content(),
                commentRequest.createdAt(),
                user,
                task
        );

        this.commentRepository.save(comment);
    }

    public List<CommentResponse> fetchAllCommentsByTaskId(final Long taskId) {
        final Task task = this.taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException("Task not found"));

        return task.comments()
                .stream()
                .map(comment -> new CommentResponse(
                        comment.id(),
                        comment.content(),
                        comment.createdAt(),
                        comment.author().id(),
                        comment.task().id())
                )
                .toList();
    }
}
