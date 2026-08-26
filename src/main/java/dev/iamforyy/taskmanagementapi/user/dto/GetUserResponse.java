package dev.iamforyy.taskmanagementapi.user.dto;

import dev.iamforyy.taskmanagementapi.user.User;
import dev.iamforyy.taskmanagementapi.user.UserRole;

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
