package dev.mantiel.taskmanagementapi.user.dto;

import dev.mantiel.taskmanagementapi.user.User;
import dev.mantiel.taskmanagementapi.user.UserRole;

public record GetUserResponse(
        Long id,
        String username,
        String email,
        UserRole role
) {

    public static GetUserResponse of(final User user) {
        return new GetUserResponse(user.id(), user.username(), user.email(), user.role());
    }


}
