package dev.iamforyy.taskmanagementapi.user;

import dev.iamforyy.taskmanagementapi.common.exception.NotFoundException;
import dev.iamforyy.taskmanagementapi.user.dto.UpdateUserRequest;
import dev.iamforyy.taskmanagementapi.user.dto.GetUserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    public UserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Page<GetUserResponse> fetchAllUsers(final int pageNumber, final int pageSize) {
        return this.userRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, "id")))
                .map(GetUserResponse::of);
    }

    public GetUserResponse fetchUserById(final Long userId) {
        return this.userRepository.findById(userId)
                .map(GetUserResponse::of)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    public String updateUserById(final Long id, final UpdateUserRequest updateUserRequest) {
        final User user = this.userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));

        if (updateUserRequest.username() != null && !updateUserRequest.username().isBlank()) {
            user.username(updateUserRequest.username());
        }

        this.userRepository.save(user);
        return "done";
    }

    public String deleteUserById(final Long id) {
        final User user = this.userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));

        this.userRepository.delete(user);
        return "done";
    }
}
