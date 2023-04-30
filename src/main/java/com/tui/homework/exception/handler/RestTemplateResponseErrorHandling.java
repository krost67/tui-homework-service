package com.tui.homework.exception.handler;

import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.tui.homework.exception.ResourceNotAcceptableException;
import com.tui.homework.exception.ResourceNotFoundException;
import java.io.IOException;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

public class RestTemplateResponseErrorHandling implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return response.getStatusCode().is4xxClientError() || response.getStatusCode().is5xxServerError();
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        if (response.getStatusCode().is4xxClientError()) {
            if (NOT_FOUND.equals(response.getStatusCode())) {
                throw new ResourceNotFoundException();
            }
            if (NOT_ACCEPTABLE.equals(response.getStatusCode())) {
                throw new ResourceNotAcceptableException();
            }
        }
    }
}
