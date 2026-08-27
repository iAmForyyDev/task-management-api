package dev.iamforyy.taskmanagementapi.auth.register;

import dev.iamforyy.taskmanagementapi.user.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AuthRegisterRequest(
        @Schema(
                description = "Username of the new user.",
                example = "JohnDoe"
        )
        @NotBlank
        @Size(min = 2, max = 16)
        String username,

        @Schema(
                description = "Email of the new user.",
                example = "johndoe@gmail.com"
        )
        @Email
        String email,

        @Schema(description = "Password for the new account. Must be at least 8 characters long.")
        @NotBlank
        String password,

        @Schema(description = "Role assigned to the new user. Defaults to USER if not specified.")
        UserRole userRole
) {
}
