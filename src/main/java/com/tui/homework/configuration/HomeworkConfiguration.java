package com.tui.homework.configuration;

import com.tui.homework.connector.github.configuration.GitHubConnectorProperties;
import com.tui.homework.exception.handler.RestTemplateResponseErrorHandling;
import com.tui.homework.logging.LoggingRequestInterceptor;
import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableConfigurationProperties({
        GitHubConnectorProperties.class
})
public class HomeworkConfiguration {

    @Value("${homework.connector.connectTimeout}")
    private Integer connectTimeout;
    @Value("${homework.connector.readTimeout}")
    private Integer readTimeout;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        RestTemplate restTemplate = restTemplateBuilder
                .setConnectTimeout(Duration.ofMillis(connectTimeout))
                .setReadTimeout(Duration.ofMillis(readTimeout))
                .errorHandler(new RestTemplateResponseErrorHandling())
                .build();

        restTemplate.getInterceptors().add(new LoggingRequestInterceptor());
        return restTemplate;
    }
}
