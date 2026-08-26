package dev.iamforyy.taskmanagementapi.user;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UserPrincipal implements UserDetails {

    private final User user;
    public UserPrincipal(final User user) {
        this.user = user;
    }

    public User user() {
        return this.user;
    }

    public UserRole userRole() {
        return this.user.role();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(
                new SimpleGrantedAuthority("ROLE_" + this.user.role().name())
        );
    }

    public Long id() {
        return this.user.id();
    }

    @Override
    public @Nullable String getPassword() {
        return this.user.passwordHash();
    }

    @Override
    public String getUsername() {
        return this.user.username();
    }
}
