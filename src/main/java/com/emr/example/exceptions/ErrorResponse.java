package com.emr.example.exceptions;

import lombok.Getter;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Getter
public class ErrorResponse {

    private final Instant timestamp;
    private final int status;
    private final String message;
    private final Map<String, List<String>> errors;

    public ErrorResponse(int status, String message) {
        this.timestamp = Instant.now();
        this.status = status;
        this.message = message;
        this.errors = null;
    }

    public ErrorResponse(int status, String message, Map<String, List<String>> errors) {
        this.timestamp = Instant.now();
        this.status = status;
        this.message = message;
        this.errors = errors;
    }
}
