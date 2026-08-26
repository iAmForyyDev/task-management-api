package dev.iamforyy.taskmanagementapi.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.jspecify.annotations.Nullable;

import java.time.Instant;
import java.util.Map;

public record ApiResponse(
        int code,
        String error,
        String message,
        Instant timestamp,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        @Nullable Map<String, String> fields
) {

    public ApiResponse(final int code, final String error, final String message, final Map<String, String> fields) {
        this(code, error, message, Instant.now(), fields);
    }

    public ApiResponse(final int code, final String error, final String message, Instant timestamp) {
        this(code, error, message, timestamp, null);
    }

    public ApiResponse(final int code, final String error, final String message) {
        this(code, error, message, Instant.now(), null);
    }

}
