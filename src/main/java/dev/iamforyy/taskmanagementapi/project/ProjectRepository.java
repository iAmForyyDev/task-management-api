package dev.iamforyy.taskmanagementapi.project;

import dev.iamforyy.taskmanagementapi.project.dto.ProjectTaskCounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Query("""
            SELECT new dev.iamforyy.taskmanagementapi.project.dto.ProjectTaskCounts(
                COUNT(t),
                COALESCE(SUM(CASE WHEN t.status = dev.iamforyy.taskmanagementapi.task.TaskStatus.TODO THEN 1L ELSE 0L END), 0L),
                COALESCE(SUM(CASE WHEN t.status = dev.iamforyy.taskmanagementapi.task.TaskStatus.IN_PROGRESS THEN 1L ELSE 0L END), 0L),
                COALESCE(SUM(CASE WHEN t.status = dev.iamforyy.taskmanagementapi.task.TaskStatus.DONE THEN 1L ELSE 0L END), 0L)
            )
            FROM Project p
            LEFT JOIN p.tasks t
            WHERE p.id = :projectId
            GROUP BY p.id
            """)
    Optional<ProjectTaskCounts> findTaskCountsByProjectId(
            final @Param("projectId") Long projectId
    );

}
