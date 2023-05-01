package com.tui.homework.exception.handler;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.tui.homework.exception.RateLimitException;
import com.tui.homework.exception.ResourceNotAvailableException;
import com.tui.homework.exception.ResourceNotFoundException;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

@Slf4j
public class RestTemplateResponseErrorHandling implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return response.getStatusCode().is4xxClientError() || response.getStatusCode().is5xxServerError();
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        if (response.getStatusCode().is4xxClientError()) {
            log.error(String.format("Error is occurred during HTTP call [%s]", response.getStatusText()));

            if (NOT_FOUND.equals(response.getStatusCode())) {
                throw new ResourceNotFoundException();
            }
            if (FORBIDDEN.equals(response.getStatusCode())) {
                if (response.getStatusText().contains("rate limit")) {
                    throw new RateLimitException();
                }

                throw new ResourceNotAvailableException();
            }
        }
    }
}
