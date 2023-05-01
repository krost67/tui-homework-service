package com.tui.homework.exception;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE;

import com.tui.homework.exception.handler.HomeworkExceptionHandler;
import com.tui.homework.model.ErrorMessage;
import com.tui.homework.model.ErrorType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;

public class HomeworkExceptionHandlerTest {

    private final HomeworkExceptionHandler handler = new HomeworkExceptionHandler();

    @Test
    public void handleNotAcceptableMediaTypeException_ShouldReturnNotAcceptable_WhenExceptionCaught() {
        // Given
        HttpMediaTypeNotAcceptableException exception = new HttpMediaTypeNotAcceptableException("test");

        // When
        ResponseEntity<?> response = handler.handleNotAcceptableMediaTypeException(exception);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(NOT_ACCEPTABLE);
        assertThat(response.getBody()).isInstanceOf(ErrorMessage.class);
        ErrorMessage body = (ErrorMessage) response.getBody();
        Assertions.assertNotNull(body);
        assertThat(body).extracting(ErrorMessage::getCode).isEqualTo(ErrorType.NOT_ACCEPTABLE_MEDIA_TYPE);
    }

    @Test
    public void handleResourceNotAvailableException_ShouldReturnForbidden_WhenExceptionCaught() {
        // Given
        ResourceNotAvailableException exception = new ResourceNotAvailableException("test");

        // When
        ResponseEntity<?> response = handler.handleNotAvailableResourceException(exception);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(FORBIDDEN);
        assertThat(response.getBody()).isInstanceOf(ErrorMessage.class);
        ErrorMessage body = (ErrorMessage) response.getBody();
        Assertions.assertNotNull(body);
        assertThat(body).extracting(ErrorMessage::getCode).isEqualTo(ErrorType.FORBIDDEN);
    }

    @Test
    public void handleRateLimitException_ShouldReturnForbidden_WhenExceptionCaught() {
        // Given
        RateLimitException exception = new RateLimitException();

        // When
        ResponseEntity<?> response = handler.handleRateLimitException(exception);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(FORBIDDEN);
        assertThat(response.getBody()).isInstanceOf(ErrorMessage.class);
        ErrorMessage body = (ErrorMessage) response.getBody();
        Assertions.assertNotNull(body);
        assertThat(body).extracting(ErrorMessage::getCode).isEqualTo(ErrorType.RATE_LIMIT);
    }

    @Test
    public void handleMissedParameterException_ShouldReturnBadRequest_WhenExceptionCaught() {
        // Given
        MissedParameterException exception = new MissedParameterException("username");

        // When
        ResponseEntity<?> response = handler.handleMissedParameterException(exception);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(BAD_REQUEST);
        assertThat(response.getBody()).isInstanceOf(ErrorMessage.class);
        ErrorMessage body = (ErrorMessage) response.getBody();
        Assertions.assertNotNull(body);
        assertThat(body).extracting(ErrorMessage::getCode).isEqualTo(ErrorType.MISSING_PARAMETER);
    }

    @Test
    public void handleResourceNotFoundException_ShouldReturnNotFound_WhenExceptionCaught() {
        // Given
        ResourceNotFoundException exception = new ResourceNotFoundException("username");

        // When
        ResponseEntity<?> response = handler.handleResourceNotFoundException(exception);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(NOT_FOUND);
        assertThat(response.getBody()).isInstanceOf(ErrorMessage.class);
        ErrorMessage body = (ErrorMessage) response.getBody();
        Assertions.assertNotNull(body);
        assertThat(body).extracting(ErrorMessage::getCode).isEqualTo(ErrorType.NOT_FOUND);
    }

    @Test
    public void defaultExceptionHandler_ShouldReturnServerError_WhenExceptionCaught() {
        // Given
        RuntimeException exception = new RuntimeException();

        // When
        ResponseEntity<?> response = handler.defaultExceptionHandler(exception);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(SERVICE_UNAVAILABLE);
        assertThat(response.getBody()).isInstanceOf(ErrorMessage.class);
        ErrorMessage body = (ErrorMessage) response.getBody();
        Assertions.assertNotNull(body);
        assertThat(body).extracting(ErrorMessage::getCode).isEqualTo(ErrorType.SERVICE_UNAVAILABLE);
    }
}
