package dev.iamforyy.taskmanagementapi.task;

import dev.iamforyy.taskmanagementapi.comment.Comment;
import dev.iamforyy.taskmanagementapi.project.Project;
import dev.iamforyy.taskmanagementapi.user.User;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column()
    private String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TaskPriority priority;

    @Column(nullable = false)
    private Instant dueDate;

    @Column(nullable = true)
    private Instant completedAt;

    @Column(nullable = false)
    private Instant createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User assignee;

    @OneToMany(mappedBy = "task", cascade =  CascadeType.REMOVE, orphanRemoval = true)
    private List<Comment> comments;

    public Task() {

    }

    public Task(
            final String title,
            final String description,
            final TaskStatus taskStatus,
            final TaskPriority taskPriority,
            final Instant dueDate,
            final Instant completedAt,
            final Instant createdAt,
            final Project project,
            final User assignee,
            final List<Comment> comments
    ) {
        this.title = title;
        this.description = description;
        this.status = taskStatus;
        this.priority = taskPriority;
        this.dueDate = dueDate;
        this.completedAt = completedAt;
        this.createdAt = createdAt;
        this.project = project;
        this.assignee = assignee;
        this.comments = comments;
    }

    public Long id() {
        return this.id;
    }

    public String title() {
        return this.title;
    }

    public void title(String title) {
        this.title = title;
    }

    public String description() {
        return this.description;
    }

    public void description(String description) {
        this.description = description;
    }

    public TaskStatus status() {
        return this.status;
    }

    public TaskPriority priority() {
        return this.priority;
    }

    public Instant dueDate() {
        return this.dueDate;
    }

    public Instant completedAt() {
        return this.completedAt;
    }

    public Instant createdAt() {
        return this.createdAt;
    }

    public Project project() {
        return this.project;
    }

    public User assignee() {
        return this.assignee;
    }

    public List<Comment> comments() {
        return this.comments;
    }
}
