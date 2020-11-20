package com.emr.example.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class CustomControllerAdvice {

    @ExceptionHandler(Exception.class)
    ResponseEntity<ErrorResponse> exception(Exception e) {

        String message = "Internal server error";

        String logMessage = buildLogMessage(e.getClass(), HttpStatus.INTERNAL_SERVER_ERROR);

        log.error(logMessage, e);

        return createResponse(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    ResponseEntity<ErrorResponse> exception(HttpRequestMethodNotSupportedException e) {

        String message = "HTTP method " + e.getMethod() + " not allowed for this endpoint";

        String logMessage = buildLogMessage(e.getClass(), HttpStatus.METHOD_NOT_ALLOWED);
        log.info(logMessage);

        return createResponse(HttpStatus.METHOD_NOT_ALLOWED, message);
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    ResponseEntity<ErrorResponse> exception(ObjectNotFoundException e) {

        String logMessage = buildLogMessage(ObjectNotFoundException.class, HttpStatus.NOT_FOUND);
        log.info(logMessage);

        return createResponse(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(InvalidRequestParameterException.class)
    ResponseEntity<ErrorResponse> exception(InvalidRequestParameterException e) {

        String message = e.getParameterName() != null ?
                "Invalid parameter " + e.getParameterName() + "; " + e.getMessage() :
                e.getMessage();

        String logMessage = buildLogMessage(InvalidRequestParameterException.class, HttpStatus.BAD_REQUEST);
        log.info(logMessage, e);

        return createResponse(HttpStatus.BAD_REQUEST, message);
    }

    private String buildLogMessage(Class<? extends Throwable> exception, HttpStatus responseCode) {

        return "Caught " + exception.getSimpleName() + "; returning " + responseCode;
    }

    private ResponseEntity<ErrorResponse> createResponse(HttpStatus status, String message) {

        var errorResponse = new ErrorResponse(status.value(), message);

        return new ResponseEntity<>(errorResponse, status);
    }

    private ResponseEntity<ErrorResponse> createErrorResponseWithErrors(HttpStatus status, String message, Map<String, List<String>> errors) {

        var errorResponse = new ErrorResponse(status.value(), message, errors);

        return new ResponseEntity<>(errorResponse, status);
    }
}
