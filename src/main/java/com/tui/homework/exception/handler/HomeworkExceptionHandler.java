package com.tui.homework.exception.handler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import com.tui.homework.exception.MissedParameterException;
import com.tui.homework.exception.RateLimitException;
import com.tui.homework.exception.ResourceNotAvailableException;
import com.tui.homework.exception.ResourceNotFoundException;
import com.tui.homework.model.ErrorMessage;
import com.tui.homework.model.ErrorType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class HomeworkExceptionHandler {

    public static final String UNEXPECTED_ERROR_MESSAGE = "An unexpected error occurred while processing your request. Please try again later or "
            + "contact support if the problem persists.";
    public static final String MISSING_PARAMETER_MESSAGE = "The [%s] parameter is required but was not provided.";
    public static final String RESOURCE_NOT_FOUND_MESSAGE = "The requested resource could not be found.";
    public static final String NOT_ACCEPTABLE_MEDIA_MESSAGE = "The requested media type is not available.";
    public static final String NOT_AVAILABLE_RESOURCE_MESSAGE = "The requested resource is not available.";
    public static final String RATE_LIMIT_MESSAGE = "API rate limit exceeded";

    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public ResponseEntity<?> handleNotAcceptableMediaTypeException(HttpMediaTypeNotAcceptableException ex) {
        log.warn(ex.getMessage(), ex);
        return ResponseEntity.status(NOT_ACCEPTABLE).contentType(APPLICATION_JSON)
                .body(new ErrorMessage(ErrorType.NOT_ACCEPTABLE_MEDIA_TYPE, NOT_ACCEPTABLE_MEDIA_MESSAGE));
    }

    @ExceptionHandler(ResourceNotAvailableException.class)
    public ResponseEntity<?> handleNotAvailableResourceException(ResourceNotAvailableException ex) {
        log.warn(ex.getMessage(), ex);
        return ResponseEntity.status(FORBIDDEN).contentType(APPLICATION_JSON)
                .body(new ErrorMessage(ErrorType.FORBIDDEN, NOT_AVAILABLE_RESOURCE_MESSAGE));
    }

    @ExceptionHandler(RateLimitException.class)
    public ResponseEntity<?> handleRateLimitException(RateLimitException ex) {
        log.warn(ex.getMessage(), ex);
        return ResponseEntity.status(FORBIDDEN).contentType(APPLICATION_JSON)
                .body(new ErrorMessage(ErrorType.RATE_LIMIT, RATE_LIMIT_MESSAGE));
    }

    @ExceptionHandler(MissedParameterException.class)
    public ResponseEntity<?> handleMissedParameterException(MissedParameterException ex) {
        log.warn(ex.getMessage(), ex);
        return ResponseEntity.status(BAD_REQUEST).contentType(APPLICATION_JSON)
                .body(new ErrorMessage(ErrorType.MISSING_PARAMETER, String.format(MISSING_PARAMETER_MESSAGE, ex.getParameterName())));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException ex) {
        log.warn(ex.getMessage(), ex);
        return ResponseEntity.status(NOT_FOUND).contentType(APPLICATION_JSON)
                .body(new ErrorMessage(ErrorType.NOT_FOUND, RESOURCE_NOT_FOUND_MESSAGE));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> defaultExceptionHandler(RuntimeException ex) {
        log.warn(ex.getMessage(), ex);
        return ResponseEntity.status(SERVICE_UNAVAILABLE).contentType(APPLICATION_JSON)
                .body(new ErrorMessage(ErrorType.SERVICE_UNAVAILABLE, UNEXPECTED_ERROR_MESSAGE));
    }
}
