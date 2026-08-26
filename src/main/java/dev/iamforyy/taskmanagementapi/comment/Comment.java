package dev.iamforyy.taskmanagementapi.comment;

import dev.iamforyy.taskmanagementapi.task.Task;
import dev.iamforyy.taskmanagementapi.user.User;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Instant createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    public Comment() {

    }

    public Comment(
            final String content,
            final Instant createdAt,
            final User author,
            final Task task
    ) {
        this.content = content;
        this.createdAt = createdAt;
        this.author = author;
        this.task = task;
    }

    public Long id() {
        return this.id;
    }

    public String content() {
        return this.content;
    }

    public void content(final String content) {
        this.content = content;
    }

    public Instant createdAt() {
        return this.createdAt;
    }

    public User author() {
        return this.author;
    }

    public Task task() {
        return this.task;
    }
}
