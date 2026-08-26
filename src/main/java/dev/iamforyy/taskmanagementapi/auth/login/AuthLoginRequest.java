package dev.iamforyy.taskmanagementapi.auth.login;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AuthLoginRequest(
        @Email
        String email,

        @NotBlank
        String password
) {
}
