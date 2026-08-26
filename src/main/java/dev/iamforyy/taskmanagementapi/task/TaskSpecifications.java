package dev.iamforyy.taskmanagementapi.task;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.UpdateSpecification;

public final class TaskSpecifications {

    public static UpdateSpecification<Task> updateByTaskId(final Long taskId) {
        return (root, _, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("id"), taskId);
    }

    public static Specification<Task> byProjectId(final Long projectId) {
        return (root, _, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("project").get("id"), projectId);
    }

    public static Specification<Task> byStatus(final TaskStatus status) {
        return (root, _, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("status"), status);
    }

    public static Specification<Task> byPriority(final TaskPriority priority) {
        return (root, _, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("priority"), priority);
    }

    public static Specification<Task> byAssignee(final Long assignee) {
        return (root, _, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("assignee").get("id"), assignee);
    }

}
