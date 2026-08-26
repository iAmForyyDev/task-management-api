package dev.iamforyy.taskmanagementapi.task;

import dev.iamforyy.taskmanagementapi.comment.dto.CommentResponse;
import dev.iamforyy.taskmanagementapi.project.dto.update.UpdateTaskRequest;
import dev.iamforyy.taskmanagementapi.task.dto.CreateCommentRequest;
import dev.iamforyy.taskmanagementapi.task.dto.TaskResponse;
import dev.iamforyy.taskmanagementapi.task.dto.UpdateTaskStatusRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;
    public TaskController(final TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/{taskId}")
    public TaskResponse fetchTaskById(final @PathVariable Long taskId) {
        return this.taskService.fetchTaskById(taskId);
    }

    @PatchMapping("/{taskId}")
    public String updateTaskById(final @PathVariable Long taskId, @Validated @RequestBody UpdateTaskRequest taskRequest) {
        return this.taskService.updateTaskById(taskId, taskRequest);
    }

    @PatchMapping("/{taskId}/status")
    public ResponseEntity<String> updateStatusByTaskId(final @PathVariable Long taskId, final @RequestBody UpdateTaskStatusRequest statusRequest) {
        return ResponseEntity.ok(this.taskService.updateTaskStatusById(taskId, statusRequest));
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTaskById(final @PathVariable Long taskId) {
        this.taskService.deleteTaskById(taskId);
        return ResponseEntity.noContent().build();
    }

    // Comments
    @PostMapping("/{taskId}/comments")
    public void createCommentByTaskId(final @PathVariable Long taskId, final @RequestBody CreateCommentRequest commentRequest) {
        this.taskService.createCommentByTaskId(taskId, commentRequest);
    }

    @GetMapping("/{taskId}/comments")
    public List<CommentResponse> fetchAllCommentsByTaskId(final @PathVariable Long taskId) {
        return this.taskService.fetchAllCommentsByTaskId(taskId);
    }

}
