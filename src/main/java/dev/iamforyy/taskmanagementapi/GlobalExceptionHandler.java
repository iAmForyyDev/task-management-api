package dev.iamforyy.taskmanagementapi;

import dev.iamforyy.taskmanagementapi.common.exception.ConflictException;
import dev.iamforyy.taskmanagementapi.common.exception.ForbiddenException;
import dev.iamforyy.taskmanagementapi.common.exception.NotFoundException;
import dev.iamforyy.taskmanagementapi.common.exception.UnauthorizedException;
import dev.iamforyy.taskmanagementapi.common.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleValidation(final MethodArgumentNotValidException exception) {
        final Map<String, String> fields = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        fieldError -> fieldError.getDefaultMessage() != null
                                ? fieldError.getDefaultMessage()
                                : "Invalid value",
                        (existing, _) -> existing
                ));

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse(
                        HttpStatus.BAD_REQUEST.value(),
                        "VALIDATION_ERROR",
                        "Request validation failed.",
                        fields
                ));
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ApiResponse> handleUnauthorized(final UnauthorizedException exception) {
        return build(HttpStatus.UNAUTHORIZED, "UNAUTHORIZED", exception.getMessage());
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ApiResponse> handleForbidden(final ForbiddenException exception) {
        return build(HttpStatus.FORBIDDEN, "AUTHORIZATION_ERROR", exception.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiResponse> handleNotFound(final NotFoundException exception) {
        return build(HttpStatus.NOT_FOUND, "NOT_FOUND", exception.getMessage());
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ApiResponse> handleConflict(final ConflictException exception) {
        return build(HttpStatus.CONFLICT, "CONFLICT", exception.getMessage());
    }

    private ResponseEntity<ApiResponse> build(
            final HttpStatus status,
            final String error,
            final String message
    ) {
        return ResponseEntity
                .status(status)
                .body(new ApiResponse(status.value(), error, message));
    }
}