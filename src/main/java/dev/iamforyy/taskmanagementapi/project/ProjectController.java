package dev.iamforyy.taskmanagementapi.project;

import dev.iamforyy.taskmanagementapi.project.dto.GetProjectStatsResponse;
import dev.iamforyy.taskmanagementapi.project.dto.ProjectRequest;
import dev.iamforyy.taskmanagementapi.project.dto.ProjectResponse;
import dev.iamforyy.taskmanagementapi.project.dto.create.CreateProjectRequest;
import dev.iamforyy.taskmanagementapi.project.dto.fetch.GetProjectResponse;
import dev.iamforyy.taskmanagementapi.task.TaskPriority;
import dev.iamforyy.taskmanagementapi.task.TaskStatus;
import dev.iamforyy.taskmanagementapi.task.dto.CreateTaskRequest;
import dev.iamforyy.taskmanagementapi.task.dto.UpdateTaskResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;
    public ProjectController(final ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    public String createProject(final @RequestBody CreateProjectRequest projectRequest, final Authentication authentication) {
        return this.projectService.createProject(projectRequest, authentication);
    }

    @GetMapping
    public Page<GetProjectResponse> fetchAllProjects(final Pageable pageable) {
        return this.projectService.fetchAllProjects(pageable);
    }

    @GetMapping("/{projectId}")
    public GetProjectResponse fetchProjectById(final @PathVariable long projectId) {
        return this.projectService.fetchByProjectId(projectId);
    }

    @PatchMapping("/{projectId}")
    public ProjectResponse updateProjectById(
            final @PathVariable long projectId,
            final @Validated @RequestBody ProjectRequest projectRequest
    ) {
        return this.projectService.updateProjectById(projectId, projectRequest);
    }

    @DeleteMapping("/{projectId}")
    public ProjectResponse deleteProjectById(final @PathVariable long projectId) {
        return this.projectService.deleteProjectById(projectId);
    }

    @GetMapping("/{projectId}/stats")
    public GetProjectStatsResponse fetchProjectStatsById(final @PathVariable Long projectId) {
        return this.projectService.fetchProjectStatsById(projectId);
    }

    // Tasks

    @PostMapping("/{projectId}/tasks")
    public String createTaskByProjectId(
            final @PathVariable Long projectId,
            final @RequestBody CreateTaskRequest createTaskRequest
    ) {
        return this.projectService.createTaskByProjectId(projectId, createTaskRequest);
    }

    @GetMapping("/{projectId}/tasks")
    public List<UpdateTaskResponse> fetchTasksByProjectId(
            final @PathVariable Long projectId,
            final @RequestParam(required = false) TaskStatus status,
            final @RequestParam(required = false) TaskPriority priority,
            final @RequestParam(required = false) Long assignee,
            final Pageable pageable
    ) {
        return this.projectService.fetchTasksByProjectId(projectId, status, priority, assignee, pageable);
    }

}
