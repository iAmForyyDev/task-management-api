package dev.iamforyy.taskmanagementapi.project;

import dev.iamforyy.taskmanagementapi.project.dto.GetProjectStatsResponse;
import dev.iamforyy.taskmanagementapi.project.dto.ProjectRequest;
import dev.iamforyy.taskmanagementapi.project.dto.ProjectResponse;
import dev.iamforyy.taskmanagementapi.project.dto.create.CreateProjectRequest;
import dev.iamforyy.taskmanagementapi.project.dto.fetch.GetProjectResponse;
import dev.iamforyy.taskmanagementapi.task.TaskPriority;
import dev.iamforyy.taskmanagementapi.task.TaskStatus;
import dev.iamforyy.taskmanagementapi.task.dto.CreateTaskRequest;
import dev.iamforyy.taskmanagementapi.task.dto.TaskResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@ApiResponse(
        responseCode = "401",
        description = "User not authorized.",
        content = @Content(schema = @Schema(implementation = dev.iamforyy.taskmanagementapi.common.response.ApiResponse.class))
)
public class ProjectController {

    private final ProjectService projectService;
    public ProjectController(final ProjectService projectService) {
        this.projectService = projectService;
    }

    @Operation(
            summary = "Create a new project.",
            description = "Creates a new project for the authenticated user.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Project created successfully.",
                            content = @Content(schema = @Schema(implementation = ProjectResponse.class))
                    )
            }
    )
    @PostMapping
    public String createProject(final @RequestBody CreateProjectRequest projectRequest, final Authentication authentication) {
        return this.projectService.createProject(projectRequest, authentication);
    }

    @Operation(
            summary = "Get a project by ID.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Project retrieved successfully.",
                            content = @Content(schema = @Schema(implementation = GetProjectResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Project not found.",
                            content = @Content(schema = @Schema(implementation = dev.iamforyy.taskmanagementapi.common.response.ApiResponse.class))
                    )
            }
    )
    @GetMapping("/{projectId}")
    public GetProjectResponse fetchProjectById(final @PathVariable long projectId) {
        return this.projectService.fetchByProjectId(projectId);
    }

    @Operation(
            summary = "Get all projects.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "All projects retrieved successfully."
                    )
            }
    )
    @GetMapping
    public Page<GetProjectResponse> fetchAllProjects(final Pageable pageable) {
        return this.projectService.fetchAllProjects(pageable);
    }

    @Operation(
            summary = "Update a project by ID.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Project updated successfully.",
                            content = @Content(schema = @Schema(implementation = ProjectResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Project not found.",
                            content = @Content(schema = @Schema(implementation = dev.iamforyy.taskmanagementapi.common.response.ApiResponse.class))
                    )
            }
    )
    @PatchMapping("/{projectId}")
    public ProjectResponse updateProjectById(
            final @PathVariable long projectId,
            final @Validated @RequestBody ProjectRequest projectRequest
    ) {
        return this.projectService.updateProjectById(projectId, projectRequest);
    }

    @Operation(
            summary = "Delete a project by ID.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Project deleted successfully.",
                            content = @Content(schema = @Schema(implementation = ProjectResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Project not found.",
                            content = @Content(schema = @Schema(implementation = dev.iamforyy.taskmanagementapi.common.response.ApiResponse.class))
                    )
            }
    )
    @DeleteMapping("/{projectId}")
    public ProjectResponse deleteProjectById(final @PathVariable long projectId) {
        return this.projectService.deleteProjectById(projectId);
    }

    @Operation(
            summary = "Get project statistics by ID.",
            description = "Returns task counts and completion percentage for a project.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Project statistics retrieved successfully.",
                            content = @Content(schema = @Schema(implementation = GetProjectStatsResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Project not found.",
                            content = @Content(schema = @Schema(implementation = dev.iamforyy.taskmanagementapi.common.response.ApiResponse.class))
                    )
            }
    )
    @GetMapping("/{projectId}/stats")
    public GetProjectStatsResponse fetchProjectStatsById(final @PathVariable Long projectId) {
        return this.projectService.fetchProjectStatsById(projectId);
    }

    // Tasks

    @Operation(
            summary = "Create a new task in a project.",
            description = "Creates a new task associated with the given project.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Task created successfully.",
                            content = @Content(schema = @Schema(implementation = TaskResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Project not found.",
                            content = @Content(schema = @Schema(implementation = dev.iamforyy.taskmanagementapi.common.response.ApiResponse.class))
                    )
            }
    )
    @PostMapping("/{projectId}/tasks")
    public String createTaskByProjectId(
            final @PathVariable Long projectId,
            final @RequestBody CreateTaskRequest createTaskRequest
    ) {
        return this.projectService.createTaskByProjectId(projectId, createTaskRequest);
    }

    @Operation(
            summary = "Get tasks for a project.",
            description = "Returns tasks belonging to a project, optionally filtered by status, priority, or assignee.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Tasks retrieved successfully.",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = TaskResponse.class)))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Project not found.",
                            content = @Content(schema = @Schema(implementation = dev.iamforyy.taskmanagementapi.common.response.ApiResponse.class))
                    )
            }
    )
    @GetMapping("/{projectId}/tasks")
    public List<TaskResponse> fetchTasksByProjectId(
            final @PathVariable Long projectId,
            final @RequestParam(required = false) TaskStatus status,
            final @RequestParam(required = false) TaskPriority priority,
            final @RequestParam(required = false) Long assignee,
            final Pageable pageable
    ) {
        return this.projectService.fetchTasksByProjectId(projectId, status, priority, assignee, pageable);
    }

}