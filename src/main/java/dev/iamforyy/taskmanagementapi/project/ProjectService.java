package dev.iamforyy.taskmanagementapi.project;

import dev.iamforyy.taskmanagementapi.common.exception.NotFoundException;
import dev.iamforyy.taskmanagementapi.project.dto.GetProjectStatsResponse;
import dev.iamforyy.taskmanagementapi.project.dto.ProjectRequest;
import dev.iamforyy.taskmanagementapi.project.dto.ProjectResponse;
import dev.iamforyy.taskmanagementapi.project.dto.ProjectTaskCounts;
import dev.iamforyy.taskmanagementapi.project.dto.create.CreateProjectRequest;
import dev.iamforyy.taskmanagementapi.project.dto.fetch.GetProjectResponse;
import dev.iamforyy.taskmanagementapi.task.*;
import dev.iamforyy.taskmanagementapi.task.dto.CreateTaskRequest;
import dev.iamforyy.taskmanagementapi.task.dto.UpdateTaskResponse;
import dev.iamforyy.taskmanagementapi.user.User;
import dev.iamforyy.taskmanagementapi.user.UserPrincipal;
import dev.iamforyy.taskmanagementapi.user.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    public ProjectService(
            final ProjectRepository projectRepository,
            final UserRepository userRepository,
            final TaskRepository taskRepository
    ) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    @Transactional
    public String createProject(final CreateProjectRequest createRequest, final Authentication authentication) {
        final UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        if (userPrincipal == null) {
            throw new NotFoundException("User not found");
        }

        final Project project = new Project(
                createRequest.name(),
                createRequest.description(),
                createRequest.color(),
                Instant.now(),
                userPrincipal.user(),
                Collections.emptyList()
        );

        this.projectRepository.save(project);
        return "Created!";
    }

    public Page<GetProjectResponse> fetchAllProjects(final Pageable pageable) {
        return this.projectRepository.findAll(pageable).map(GetProjectResponse::of);
    }

    public GetProjectResponse fetchByProjectId(final Long projectId) {
        return this.projectRepository.findById(projectId)
                .map(GetProjectResponse::of)
                .orElseThrow(() -> new NotFoundException("Project not found"));
    }

    public ProjectResponse updateProjectById(final Long projectId, final ProjectRequest projectRequest) {
        final Project project = this.projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundException("Project not found"));

        if (projectRequest.name() != null && !projectRequest.name().isBlank()) {
            project.name(projectRequest.name());
        }

        if (projectRequest.description() != null) {
            project.description(projectRequest.description());
        }

        if (projectRequest.color() != null) {
            project.color(projectRequest.color());
        }

        this.projectRepository.save(project);
        return new ProjectResponse("updated");
    }

    public ProjectResponse deleteProjectById(final long id) {
        final Project project = this.projectRepository.findById(id)
                .orElseThrow();

        this.projectRepository.delete(project);
        return new ProjectResponse("deleted");
    }

    public String createTaskByProjectId(final Long projectId, final CreateTaskRequest createRequest) {
        final Project project = this.projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundException("Project not found"));

        final User assignedUser = this.userRepository.findById(createRequest.userAssigneeId())
                .orElseThrow(() -> new NotFoundException("Assignee not found"));

        final Task task = new Task(
                createRequest.title(),
                createRequest.description(),
                createRequest.status(),
                createRequest.priority(),
                createRequest.dueDate(),
                createRequest.completedAt(),
                createRequest.createdAt(),
                project,
                assignedUser,
                new ArrayList<>()
        );

        this.taskRepository.save(task);
        return "done";
    }

    public List<UpdateTaskResponse> fetchTasksByProjectId(
            final Long projectId,
            final TaskStatus status,
            final TaskPriority priority,
            final Long assignee,
            final Pageable pageable
    ) {

        Specification<Task> specification =
                TaskSpecifications.byProjectId(projectId);

        if (status != null) {
            specification = specification.and(
                    TaskSpecifications.byStatus(status)
            );
        }

        if (priority != null) {
            specification = specification.and(
                    TaskSpecifications.byPriority(priority)
            );
        }

        if (assignee != null) {
            specification = specification.and(
                    TaskSpecifications.byAssignee(assignee)
            );
        }

        return this.taskRepository
                .findAll(specification, pageable)
                .map(task -> new UpdateTaskResponse(
                                task.id(),
                                task.title(),
                                task.description(),
                                task.status(),
                                task.priority(),
                                task.dueDate(),
                                task.completedAt(),
                                task.createdAt(),
                                task.project(),
                                task.assignee()
                        )
                )
                .toList();
    }

    public GetProjectStatsResponse fetchProjectStatsById(final Long projectId) {
        final ProjectTaskCounts counts = this.projectRepository.findTaskCountsByProjectId(projectId)
                .orElseThrow(() -> new NotFoundException("Project not found"));

        final long completionPercentage = counts.total() == 0
                ? 0
                : Math.round((counts.done() * 100.0) / counts.total());

        return new GetProjectStatsResponse(
                counts.total(),
                counts.todo(),
                counts.inProgress(),
                counts.done(),
                completionPercentage
        );
    }

}
