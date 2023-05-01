package com.tui.homework.connector.github;

import static com.tui.homework.TestData.TEST_FORK_REPOSITORY_NAME;
import static com.tui.homework.TestData.TEST_GITHUB_HOST;
import static com.tui.homework.TestData.TEST_GITHUB_REPOS_URI;
import static com.tui.homework.TestData.TEST_REPOSITORY_NAME;
import static com.tui.homework.TestData.TEST_USERNAME;
import static com.tui.homework.TestData.prepareGitHubRepository;
import static com.tui.homework.TestData.prepareRepository;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.tui.homework.connector.IRepositoryConnector;
import com.tui.homework.connector.github.configuration.GitHubConnectorProperties;
import com.tui.homework.connector.github.model.GitHubRepository;
import com.tui.homework.connector.github.service.GitHubRepositoryConnector;
import com.tui.homework.connector.github.service.mapper.IGitHubRepositoryMapper;
import com.tui.homework.model.Repository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
public class IGitHubRepositoryConnectorTest {

    @Mock
    private RestTemplate restTemplate;
    @Mock
    private GitHubConnectorProperties properties;
    @Mock
    private IGitHubRepositoryMapper repositoryMapper;

    private IRepositoryConnector repositoryConnector;

    @BeforeEach
    public void setup() {
        repositoryConnector = new GitHubRepositoryConnector(restTemplate,
                                                            properties,
                                                            repositoryMapper);
    }

    @Test
    public void fetchRepositories_ShouldReturnRepositoryList_WhenResourcesExist() {
        // Given
        GitHubRepository gitHubRepository = prepareGitHubRepository(TEST_REPOSITORY_NAME, false);
        GitHubRepository gitHubForkRepository = prepareGitHubRepository(TEST_FORK_REPOSITORY_NAME, true);
        Repository repository = prepareRepository();
        when(properties.getHost()).thenReturn(TEST_GITHUB_HOST);
        when(properties.getReposUri()).thenReturn(TEST_GITHUB_REPOS_URI);
        when(restTemplate.getForEntity(anyString(), any(), anyMap()))
                .thenReturn(ResponseEntity.ok().body(new GitHubRepository[]{gitHubForkRepository, gitHubRepository}));
        when(repositoryMapper.toRepository(gitHubRepository)).thenReturn(repository);

        // When
        List<Repository> result = repositoryConnector.fetchRepositories(TEST_USERNAME, 1, 30);

        // Then
        verify(restTemplate, times(1)).getForEntity(
                eq(TEST_GITHUB_HOST + TEST_GITHUB_REPOS_URI + "?type=all&page=1&per_page=30"),
                any(), anyMap()
        );
        assertThat(result).hasSize(1).containsExactly(repository);
    }
}
