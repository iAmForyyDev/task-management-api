package dev.iamforyy.taskmanagementapi.auth.register;

import dev.iamforyy.taskmanagementapi.user.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AuthRegisterRequest(
        @NotBlank
        @Size(min = 2, max = 16)
        String username,

        @Email
        String email,

        @NotBlank
        String password,

        UserRole userRole
) {
}
