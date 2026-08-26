package dev.iamforyy.taskmanagementapi.user;

import dev.iamforyy.taskmanagementapi.user.dto.UpdateUserRequest;
import dev.iamforyy.taskmanagementapi.user.dto.GetUserResponse;
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

    @GetMapping
    public Page<GetUserResponse> fetchAllUsers(
            final @RequestParam(defaultValue = "0") int pageNumber,
            final @RequestParam(defaultValue = "10") int pageSize
    ) {
        return this.userService.fetchAllUsers(pageNumber, pageSize);
    }

    @GetMapping("/me")
    public GetUserResponse me(final Authentication authentication) {
        final UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        return this.userService.fetchUserById(userPrincipal.id());
    }

    @PatchMapping("/me")
    public String update(final Authentication authentication, final @Validated @RequestBody UpdateUserRequest updateUserRequest) {
        final UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        return this.userService.updateUserById(userPrincipal.id(), updateUserRequest);
    }

    @DeleteMapping("/me")
    public String delete(final Authentication authentication) {
        final UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        return this.userService.deleteUserById(userPrincipal.id());
    }

}
