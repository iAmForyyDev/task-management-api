package dev.iamforyy.taskmanagementapi;

import dev.iamforyy.taskmanagementapi.user.User;
import dev.iamforyy.taskmanagementapi.user.UserRepository;
import dev.iamforyy.taskmanagementapi.user.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestContainerConfig.class)
@Transactional
public class AuthControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired UserRepository userRepository;
    @Autowired PasswordEncoder passwordEncoder;

    @Test
    void shouldRegisterUser() throws Exception {
        final String request = """
                {
                  "username": "newUser",
                  "email": "new@example.com",
                  "password": "password123",
                  "userRole": "ADMIN"
                }
                """;

        this.mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isCreated());

        final User user = this.userRepository.findByUsername("newUser")
                .orElseThrow();

        assertEquals("newUser", user.username());
        assertEquals("new@example.com", user.email());
        assertNotEquals("password123", user.passwordHash());
        assertTrue(this.passwordEncoder.matches("password123", user.passwordHash()));
    }

    @Test
    void shouldLoginUserSuccessfully() throws Exception {
        this.userRepository.save(
                new User(
                        "newUser",
                        "new@example.com",
                        this.passwordEncoder.encode("password123"),
                        UserRole.USER
                )
        );

        final String request = """
                {
                  "email": "new@example.com",
                  "password": "password123"
                }
                """;

        this.mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").isNotEmpty())
                .andExpect(jsonPath("$.refreshToken").isNotEmpty());
    }

}
