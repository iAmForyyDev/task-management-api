package dev.iamforyy.taskmanagementapi.auth;

import dev.iamforyy.taskmanagementapi.security.RateLimiter;
import dev.iamforyy.taskmanagementapi.user.User;
import dev.iamforyy.taskmanagementapi.user.UserPrincipal;
import dev.iamforyy.taskmanagementapi.user.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final RateLimiter rateLimiter;

    public JwtAuthenticationFilter(
            final JwtService jwtService,
            final UserRepository userRepository,
            final RateLimiter rateLimiter
    ) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.rateLimiter = rateLimiter;
    }

    @Override
    protected void doFilterInternal(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final FilterChain filterChain
    ) throws ServletException, IOException {
        final String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String token = header.substring(7);
        if (!this.jwtService.isValid(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        final Long userId = Long.parseLong(this.jwtService.extractSubject(token));
        final User user = this.userRepository.findById(userId).orElse(null);
        if (user != null) {
            final UserPrincipal userPrincipal = new UserPrincipal(user);
            final UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userPrincipal,
                    null,
                    userPrincipal.getAuthorities()
            );

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        filterChain.doFilter(request, response);
    }
}
