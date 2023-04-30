package com.tui.homework.connector.github.service;

import static com.tui.homework.connector.github.utils.GitHubParameters.REPO_NAME;
import static com.tui.homework.connector.github.utils.GitHubParameters.USERNAME_PARAM;

import com.tui.homework.connector.IBranchConnector;
import com.tui.homework.connector.github.configuration.GitHubConnectorProperties;
import com.tui.homework.connector.github.model.GitHubBranch;
import com.tui.homework.connector.github.model.GitHubRepository;
import com.tui.homework.connector.github.service.mapper.IGitHubBranchMapper;
import com.tui.homework.exception.ResourceNotFoundException;
import com.tui.homework.model.Branch;
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

@Slf4j
@Service
@RequiredArgsConstructor
public class GitHubBranchConnector implements IBranchConnector {

    private final RestTemplate restTemplate;
    private final GitHubConnectorProperties properties;
    private final IGitHubBranchMapper mapper;

    @Override
    public List<Branch> fetchBranches(String username, String repositoryName) {
        ResponseEntity<GitHubBranch[]> response = executeGitHubBranchesGet(username, repositoryName);
        return Arrays.stream(Objects.requireNonNull(response.getBody()))
                .map(mapper::toBranch)
                .collect(Collectors.toList());
    }

    private ResponseEntity<GitHubBranch[]> executeGitHubBranchesGet(String username, String repositoryName) {
        return restTemplate.getForEntity(
                properties.getHost() + properties.getBranchesUri(),
                GitHubBranch[].class,
                Map.of(USERNAME_PARAM, username,
                       REPO_NAME, repositoryName));
    }
}
