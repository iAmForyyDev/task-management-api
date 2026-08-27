package dev.iamforyy.taskmanagementapi.user.dto;

import dev.iamforyy.taskmanagementapi.user.User;
import dev.iamforyy.taskmanagementapi.user.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;

public record GetUserResponse(

        @Schema(
                description = "Unique identifier of the user.",
                example = "1"
        )
        Long id,

        @Schema(
                description = "Username of the user.",
                example = "JohnDoe"
        )
        String username,

        @Schema(
                description = "Email of the user.",
                example = "jhondoe@gmail.com"
        )
        String email,

        @Schema(
                description = "Role of the user.",
                example = "USER"
        )
        UserRole role
) {

    public static GetUserResponse of(final User user) {
        return new GetUserResponse(user.id(), user.username(), user.email(), user.role());
    }


}
