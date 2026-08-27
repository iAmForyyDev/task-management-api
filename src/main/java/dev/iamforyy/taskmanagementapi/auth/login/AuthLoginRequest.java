package dev.iamforyy.taskmanagementapi.auth.login;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AuthLoginRequest(
        @Schema(
                description = "Email used to sign in",
                example = "johndoe@gmail.com"
        )
        @Email
        String email,

        @Schema(
                description = "Password used to sign in."
        )
        @NotBlank
        String password
) {
}
