package com.example.orderservice.dto;

import java.time.LocalDateTime;

public class ApiError {

    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
    private String traceId;

    public ApiError(
            LocalDateTime timestamp,
            int status,
            String error,
            String message,
            String path,
            String traceId) {

        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
        this.traceId = traceId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }

    public String getTraceId() {
        return traceId;
    }
}
