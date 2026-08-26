package dev.iamforyy.taskmanagementapi.user;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String passwordHash;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    public User() {

    }

    public User(
            final Long id,
            final String username,
            final String email,
            final String passwordHash,
            final UserRole role
    ) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
    }

    public User(
            final String username,
            final String email,
            final String passwordHash,
            final UserRole userRole
    ) {
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = userRole;
    }

    public Long id() {
        return this.id;
    }

    public String username() {
        return this.username;
    }

    public void username(final String username) {
        this.username = username;
    }

    public String email() {
        return this.email;
    }

    public String passwordHash() {
        return this.passwordHash;
    }

    public UserRole role() {
        return this.role;
    }
}
