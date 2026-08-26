package dev.iamforyy.taskmanagementapi;

import dev.iamforyy.taskmanagementapi.auth.JwtService;
import dev.iamforyy.taskmanagementapi.user.User;
import dev.iamforyy.taskmanagementapi.user.UserRepository;
import dev.iamforyy.taskmanagementapi.user.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestContainerConfig.class)
@Transactional
public class UserControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired UserRepository userRepository;
    @Autowired JwtService jwtService;

    @Test
    void shouldReturnUnauthorizedWithoutToken() throws Exception {
        this.mockMvc.perform(get("/api/users/me"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldReturnUnauthorizedWithInvalidToken() throws Exception {
        this.mockMvc.perform(get("/api/users/me")
                        .header("Authorization", "Bearer invalid")
                )
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldReturnAuthorizedWithValidToken() throws Exception {
        final User user = this.userRepository.save(new User(
                "newUser",
                "new@example.com",
                "password123",
                UserRole.USER
        ));

        final String accessToken = this.jwtService.generateToken(user);
        this.mockMvc.perform(get("/api/users/me")
                        .header("Authorization", "Bearer " + accessToken)
                )
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnCorrectUserJson() throws Exception {
        final User user = this.userRepository.save(new User(
                "newUser",
                "new@example.com",
                "password123",
                UserRole.USER
        ));

        final String accessToken = this.jwtService.generateToken(user);
        this.mockMvc.perform(get("/api/users/me")
                        .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.id()))
                .andExpect(jsonPath("$.username").value(user.username()))
                .andExpect(jsonPath("$.email").value(user.email()))
                .andExpect(jsonPath("$.role").value(user.role().name()));
    }

    @Test
    void shouldReturnAuthenticatedUser() throws Exception {
        final User userA = this.userRepository.save(new User(
                "userA",
                "a@example.com",
                "passwordHashed",
                UserRole.USER
        ));

        final User userB = this.userRepository.save(new User(
                "userB",
                "b@example.com",
                "passwordHashed",
                UserRole.USER
        ));

        final String accessToken = this.jwtService.generateToken(userA);
        this.mockMvc.perform(get("/api/users/me")
                .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userA.id()))
                .andExpect(jsonPath("$.username").value(userA.username()))
                .andExpect(jsonPath("$.email").value(userA.email()))
                .andExpect(jsonPath("$.role").value(userA.role().name()));
    }

}
