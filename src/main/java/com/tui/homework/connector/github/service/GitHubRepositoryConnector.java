package com.tui.homework.connector.github.service;

import static com.tui.homework.connector.github.utils.GitHubParameters.PAGE_PARAM;
import static com.tui.homework.connector.github.utils.GitHubParameters.PER_PAGE_PARAM;
import static com.tui.homework.connector.github.utils.GitHubParameters.TYPE_PARAM;
import static com.tui.homework.connector.github.utils.GitHubParameters.USERNAME_PARAM;

import com.tui.homework.connector.IRepositoryConnector;
import com.tui.homework.connector.github.configuration.GitHubConnectorProperties;
import com.tui.homework.connector.github.model.GitHubRepository;
import com.tui.homework.connector.github.service.mapper.IGitHubRepositoryMapper;
import com.tui.homework.model.Repository;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Service
@RequiredArgsConstructor
public class GitHubRepositoryConnector implements IRepositoryConnector {

    private final RestTemplate restTemplate;
    private final GitHubConnectorProperties properties;
    private final IGitHubRepositoryMapper mapper;

    @Override
    public List<Repository> fetchRepositories(String username, Integer pageNumber, Integer pageSize) {
        ResponseEntity<GitHubRepository[]> response = executeGitHubReposGet(username, pageNumber, pageSize);
        return Arrays.stream(Objects.requireNonNull(response.getBody()))
                .filter(e -> !e.isFork())
                .map(mapper::toRepository)
                .collect(Collectors.toList());
    }

    private ResponseEntity<GitHubRepository[]> executeGitHubReposGet(String username, Integer pageNumber, Integer pageSize) {
        return restTemplate.getForEntity(
                buildGitHubReposUrl(pageNumber, pageSize),
                GitHubRepository[].class,
                Map.of(USERNAME_PARAM, username));
    }

    private String buildGitHubReposUrl(Integer pageNumber, Integer pageSize) {
        return UriComponentsBuilder.fromHttpUrl(properties.getHost() + properties.getReposUri())
                .queryParam(TYPE_PARAM, "all")
                .queryParam(PAGE_PARAM, String.valueOf(pageNumber))
                .queryParam(PER_PAGE_PARAM, String.valueOf(pageSize))
                .encode()
                .toUriString();
    }
}
