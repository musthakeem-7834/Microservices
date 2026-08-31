package com.example.orderservice.exception;

import com.example.orderservice.dto.ApiError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.example.orderservice.exception.UserServiceUnavailableException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.example.orderservice.dto.ApiError;

import java.time.LocalDateTime;
import java.util.UUID;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiError> handleUserNotFound(
            UserNotFoundException ex,
            HttpServletRequest request) {

        ApiError error = new ApiError(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "USER_NOT_FOUND",
                ex.getMessage(),
                request.getRequestURI(),
                generateTraceId()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(error);
    }

    @ExceptionHandler(DuplicateUserException.class)
    public ResponseEntity<ApiError> handleDuplicateUser(
            DuplicateUserException ex,
            HttpServletRequest request) {

        ApiError error = new ApiError(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                "DUPLICATE_USER",
                ex.getMessage(),
                request.getRequestURI(),
                generateTraceId()
        );

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(error);
    }

    @ExceptionHandler(InvalidOrderException.class)
    public ResponseEntity<ApiError> handleInvalidOrder(
            InvalidOrderException ex,
            HttpServletRequest request) {

        ApiError error = new ApiError(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "INVALID_ORDER",
                ex.getMessage(),
                request.getRequestURI(),
                generateTraceId()
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationException(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(fieldError ->
                        fieldError.getField()
                                + ": "
                                + fieldError.getDefaultMessage())
                .orElse("Invalid request");

        ApiError error = new ApiError(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "VALIDATION_ERROR",
                message,
                request.getRequestURI(),
                generateTraceId()
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }
    
    @ExceptionHandler(UserServiceUnavailableException.class)
    public ResponseEntity<ApiError> handleUserServiceUnavailable(
            UserServiceUnavailableException ex,
            HttpServletRequest request) {

        ApiError error = new ApiError(
                LocalDateTime.now(),
                HttpStatus.SERVICE_UNAVAILABLE.value(),
                "USER_SERVICE_UNAVAILABLE",
                ex.getMessage(),
                request.getRequestURI(),
                generateTraceId()
        );

        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGenericException(
            Exception ex,
            HttpServletRequest request) {

        ApiError error = new ApiError(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "INTERNAL_SERVER_ERROR",
                "An unexpected error occurred",
                request.getRequestURI(),
                generateTraceId()
        );

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(error);
    }

    private String generateTraceId() {
        return UUID.randomUUID().toString();
    }
    
    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ApiError> handleOrderNotFound(
            OrderNotFoundException ex,
            HttpServletRequest request) {

        ApiError error = new ApiError(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "ORDER_NOT_FOUND",
                ex.getMessage(),
                request.getRequestURI(),
                generateTraceId()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(error);
    }
    
    @ExceptionHandler(DuplicateOrderException.class)
    public ResponseEntity<ApiError> handleDuplicateOrder(
            DuplicateOrderException ex,
            HttpServletRequest request) {

        ApiError error = new ApiError(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                "DUPLICATE_ORDER",
                ex.getMessage(),
                request.getRequestURI(),
                generateTraceId()
        );

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(error);
    }
    
}