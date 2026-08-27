package dev.iamforyy.taskmanagementapi.security;

import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class RateLimiterFilter extends OncePerRequestFilter {

    private final RateLimiter rateLimiter;
    public RateLimiterFilter(final RateLimiter rateLimiter) {
        this.rateLimiter = rateLimiter;
    }

    @Override
    protected void doFilterInternal(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final FilterChain filterChain
    ) throws ServletException, IOException {
        final String key = request.getRemoteAddr();
        final Bucket bucket = this.rateLimiter.getBucket(key);
        final ConsumptionProbe probe = bucket.tryConsumeAndReturnRemaining(1);

        if (!probe.isConsumed()) {
            final long waitSeconds = (long) Math.ceil(
                    probe.getNanosToWaitForRefill() / 1_000_000_000.0
            );

            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.setHeader("Retry-After", Long.toString(waitSeconds));
            response.setHeader("X-Rate-Limit-Remaining", "0");
            response.setContentType("text/plain");
            response.getWriter().append("Too many requests.");
            return;
        }

        response.setHeader("X-Rate-Limit-Remaining", Long.toString(probe.getRemainingTokens()));
        filterChain.doFilter(request, response);
    }
}
