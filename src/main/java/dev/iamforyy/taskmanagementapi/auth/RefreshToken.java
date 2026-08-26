package dev.iamforyy.taskmanagementapi.auth;

import dev.iamforyy.taskmanagementapi.user.User;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "refresh_token")
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String tokenHash;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private Instant expiredAt;

    @Column(nullable = false)
    private boolean revoked;

    @Column(nullable = false)
    private Instant createdAt;

    public RefreshToken() {

    }

    public RefreshToken(
            final String tokenHash,
            final User user,
            final Instant expiredAt
    ) {
        this.tokenHash = tokenHash;
        this.user = user;
        this.expiredAt = expiredAt;
        this.revoked = false;
        this.createdAt = Instant.now();
    }

    public Long id() {
        return this.id;
    }

    public String tokenHash() {
        return this.tokenHash;
    }

    public User user() {
        return this.user;
    }

    public Instant expiredAt() {
        return this.expiredAt;
    }

    public void revoke() {
        this.revoked = true;
    }

    public boolean revoked() {
        return this.revoked;
    }

    public Instant createdAt() {
        return this.createdAt;
    }

    public boolean isExpired() {
        return Instant.now().isAfter(this.expiredAt);
    }

}
