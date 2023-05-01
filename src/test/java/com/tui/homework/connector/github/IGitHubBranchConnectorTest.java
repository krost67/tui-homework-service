package com.tui.homework.connector.github;

import static com.tui.homework.TestData.TEST_GITHUB_BRANCHES_URI;
import static com.tui.homework.TestData.TEST_GITHUB_HOST;
import static com.tui.homework.TestData.TEST_REPOSITORY_NAME;
import static com.tui.homework.TestData.TEST_USERNAME;
import static com.tui.homework.TestData.prepareBranch;
import static com.tui.homework.TestData.prepareGitHubBranch;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.tui.homework.connector.IBranchConnector;
import com.tui.homework.connector.github.configuration.GitHubConnectorProperties;
import com.tui.homework.connector.github.model.GitHubBranch;
import com.tui.homework.connector.github.service.GitHubBranchConnector;
import com.tui.homework.connector.github.service.mapper.IGitHubBranchMapper;
import com.tui.homework.model.Branch;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
public class IGitHubBranchConnectorTest {

    @Mock
    private RestTemplate restTemplate;
    @Mock
    private GitHubConnectorProperties properties;
    @Mock
    private IGitHubBranchMapper branchMapper;

    private IBranchConnector branchConnector;

    @BeforeEach
    public void setup() {
        branchConnector = new GitHubBranchConnector(restTemplate,
                                                    properties,
                                                    branchMapper);
    }

    @Test
    public void fetchBranches_ShouldReturnBranchesList_WhenResourcesExist() {
        // Given
        GitHubBranch gitHubBranch = prepareGitHubBranch();
        Branch branch = prepareBranch();
        when(properties.getHost()).thenReturn(TEST_GITHUB_HOST);
        when(properties.getBranchesUri()).thenReturn(TEST_GITHUB_BRANCHES_URI);
        when(restTemplate.getForEntity(anyString(), any(), anyMap()))
                .thenReturn(ResponseEntity.ok().body(new GitHubBranch[]{gitHubBranch}));
        when(branchMapper.toBranch(gitHubBranch)).thenReturn(branch);

        // When
        List<Branch> result = branchConnector.fetchBranches(TEST_USERNAME, TEST_REPOSITORY_NAME);

        // Then
        verify(restTemplate, times(1)).getForEntity(
                eq(TEST_GITHUB_HOST + TEST_GITHUB_BRANCHES_URI), any(), anyMap()
        );
        assertThat(result).hasSize(1).containsExactly(branch);
    }
}
