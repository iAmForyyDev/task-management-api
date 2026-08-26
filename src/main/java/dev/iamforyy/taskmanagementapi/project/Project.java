package dev.iamforyy.taskmanagementapi.project;

import dev.iamforyy.taskmanagementapi.task.Task;
import dev.iamforyy.taskmanagementapi.user.User;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String color;

    @Column(nullable = false)
    private Instant createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User owner;

    @OneToMany(mappedBy = "project", cascade =  CascadeType.REMOVE, orphanRemoval = true)
    private List<Task> tasks;

    public Project() {

    }

    public Project(
            final String name,
            final String description,
            final String color,
            final Instant createdAt,
            final User owner,
            final List<Task> tasks
    ) {
        this.name = name;
        this.description = description;
        this.color = color;
        this.createdAt = createdAt;
        this.owner = owner;
        this.tasks = tasks;
    }

    public Long id() {
        return this.id;
    }

    public String name() {
        return this.name;
    }

    public void name(final String name) {
        this.name = name;
    }

    public String description() {
        return this.description;
    }

    public void description(final String description) {
        this.description = description;
    }

    public String color() {
        return this.color;
    }

    public void color(final String color) {
        this.color = color;
    }

    public Instant createdAt() {
        return this.createdAt;
    }

    public User owner() {
        return this.owner;
    }

    public List<Task> tasks() {
        return this.tasks;
    }
}
