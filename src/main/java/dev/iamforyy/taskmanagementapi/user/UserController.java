package dev.iamforyy.taskmanagementapi.user;

import dev.iamforyy.taskmanagementapi.user.dto.GetUserResponse;
import dev.iamforyy.taskmanagementapi.user.dto.UpdateUserRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Get all registered users.")
    @GetMapping
    public Page<GetUserResponse> fetchAllUsers(
            @Parameter(
                    description = "Zero-based page number.",
                    example = "0"
            )
            final @RequestParam(defaultValue = "0") int pageNumber,

            @Parameter(
                    description = "Number of users per page.",
                    example = "10"
            )
            final @RequestParam(defaultValue = "10") int pageSize
    ) {
        return this.userService.fetchAllUsers(pageNumber, pageSize);
    }

    @Operation(summary = "Get current authenticated user.")
    @GetMapping("/me")
    public GetUserResponse me(final Authentication authentication) {
        final UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        return this.userService.fetchUserById(userPrincipal.id());
    }

    @Operation(summary = "Update the current authenticated user.")
    @PatchMapping("/me")
    public String update(final Authentication authentication, final @Validated @RequestBody UpdateUserRequest updateUserRequest) {
        final UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        return this.userService.updateUserById(userPrincipal.id(), updateUserRequest);
    }

    @Operation(summary = "Delete the current authenticated user.")
    @DeleteMapping("/me")
    public String delete(final Authentication authentication) {
        final UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        return this.userService.deleteUserById(userPrincipal.id());
    }

}
