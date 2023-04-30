package com.tui.homework.logging;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

@Slf4j
public class LoggingRequestInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        traceRequest(request, body);
        return execution.execute(request, body);
    }

    private void traceRequest(HttpRequest request, byte[] body) {
        log.debug(String.format("Calling [%s] with: Method [%s] Headers [%s] Request body [%s]",
                               request.getURI(), request.getMethod(), request.getHeaders(), new String(body, StandardCharsets.UTF_8)));
    }
}
