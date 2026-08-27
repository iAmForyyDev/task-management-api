package dev.iamforyy.taskmanagementapi.task;

import dev.iamforyy.taskmanagementapi.comment.dto.CommentResponse;
import dev.iamforyy.taskmanagementapi.project.dto.update.UpdateTaskRequest;
import dev.iamforyy.taskmanagementapi.task.dto.CreateCommentRequest;
import dev.iamforyy.taskmanagementapi.task.dto.TaskResponse;
import dev.iamforyy.taskmanagementapi.task.dto.UpdateTaskStatusRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@ApiResponse(
        responseCode = "404",
        description = "Task with that ID not found.",
        content = @Content(schema = @Schema(implementation = dev.iamforyy.taskmanagementapi.common.response.ApiResponse.class))
)
public class TaskController {

    private final TaskService taskService;
    public TaskController(final TaskService taskService) {
        this.taskService = taskService;
    }

    @Operation(
            summary = "Get task by ID.", responses = {
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = TaskResponse.class))
            )
    })
    @GetMapping("/{taskId}")
    public TaskResponse fetchTaskById(final @PathVariable Long taskId) {
        return this.taskService.fetchTaskById(taskId);
    }

    @Operation(summary = "Update task")
    @PatchMapping("/{taskId}")
    public String updateTaskById(final @PathVariable Long taskId, @Validated @RequestBody UpdateTaskRequest taskRequest) {
        return this.taskService.updateTaskById(taskId, taskRequest);
    }

    @Operation(summary = "Update a task's status.")
    @PatchMapping("/{taskId}/status")
    public ResponseEntity<String> updateStatusByTaskId(final @PathVariable Long taskId, final @RequestBody UpdateTaskStatusRequest statusRequest) {
        return ResponseEntity.ok(this.taskService.updateTaskStatusById(taskId, statusRequest));
    }

    @Operation(summary = "Delete a task.")
    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTaskById(final @PathVariable Long taskId) {
        this.taskService.deleteTaskById(taskId);
        return ResponseEntity.noContent().build();
    }

    // Comments
    @Operation(summary = "Create a comment for a task.")
    @PostMapping("/{taskId}/comments")
    public void createCommentByTaskId(final @PathVariable Long taskId, final @RequestBody CreateCommentRequest commentRequest) {
        this.taskService.createCommentByTaskId(taskId, commentRequest);
    }

    @Operation(
            summary = "Get all comments for a task.",
            responses = {
                    @ApiResponse(
                            description = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = CommentResponse.class)
                                    )
                            )
                    )
            }
    )
    @GetMapping("/{taskId}/comments")
    public List<CommentResponse> fetchAllCommentsByTaskId(final @PathVariable Long taskId) {
        return this.taskService.fetchAllCommentsByTaskId(taskId);
    }

}
