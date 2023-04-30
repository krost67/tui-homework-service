package com.tui.homework.exception.handler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import com.tui.homework.exception.MissedParameterException;
import com.tui.homework.exception.ResourceNotAcceptableException;
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

    private static final String UNEXPECTED_ERROR_MESSAGE = "An unexpected error occurred while processing your request. Please try again later or contact support if the problem persists.";
    private static final String MISSING_PARAMETER_MESSAGE = "The [%s] parameter is required but was not provided.";
    private static final String RESOURCE_NOT_FOUND_MESSAGE = "The requested resource could not be found.";
    private static final String NOT_ACCEPTABLE_MEDIA_MESSAGE = "The requested media type is not available.";
    private static final String NOT_ACCEPTABLE_RESOURCE_MESSAGE = "The requested resource is not available.";

    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public ResponseEntity<?> handleNotAcceptableMediaTypeException(HttpMediaTypeNotAcceptableException ex) {
        log.warn(ex.getMessage(), ex);
        return ResponseEntity.status(NOT_ACCEPTABLE).contentType(APPLICATION_JSON)
                .body(new ErrorMessage(ErrorType.NOT_ACCEPTABLE_MEDIA_TYPE, NOT_ACCEPTABLE_MEDIA_MESSAGE));
    }

    @ExceptionHandler(ResourceNotAcceptableException.class)
    public ResponseEntity<?> handleNotAcceptableResourceException(ResourceNotAcceptableException ex) {
        log.warn(ex.getMessage(), ex);
        return ResponseEntity.status(NOT_ACCEPTABLE).contentType(APPLICATION_JSON)
                .body(new ErrorMessage(ErrorType.NOT_ACCEPTABLE_RESOURCE, NOT_ACCEPTABLE_RESOURCE_MESSAGE));
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
