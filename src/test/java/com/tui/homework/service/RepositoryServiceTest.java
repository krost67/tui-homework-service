package com.tui.homework.service;

import static com.tui.homework.TestData.TEST_BRANCH_NAME;
import static com.tui.homework.TestData.TEST_BRANCH_SHA;
import static com.tui.homework.TestData.TEST_REPOSITORY_NAME;
import static com.tui.homework.TestData.TEST_USERNAME;
import static com.tui.homework.TestData.prepareBranch;
import static com.tui.homework.TestData.prepareRepository;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.tui.homework.connector.IRepositoryConnector;
import com.tui.homework.model.Branch;
import com.tui.homework.model.Repository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class RepositoryServiceTest {

    @Mock
    private IRepositoryConnector repositoryConnector;
    @Mock
    private IBranchService branchService;

    private IRepositoryService repositoryService;

    @BeforeEach
    public void setup() {
        repositoryService = new RepositoryService(repositoryConnector, branchService);
    }

    @Test
    public void getRepositories_ShouldReturnRepositories_WhenValidParametersArePassed() {
        // Given
        Repository repository = prepareRepository();
        Branch branch = prepareBranch();
        when(repositoryConnector.fetchRepositories(eq(TEST_USERNAME), anyInt(), anyInt()))
                .thenReturn(List.of(repository));
        when(branchService.getBranches(TEST_USERNAME, repository.getName()))
                .thenReturn(List.of(branch));

        // When
        List<Repository> result = repositoryService.getRepositories(TEST_USERNAME, 1, 30);

        // Then
        assertThat(result).hasSize(1);
        assertEquals(TEST_REPOSITORY_NAME, result.get(0).getName());
        assertEquals(TEST_USERNAME, result.get(0).getOwner());

        assertThat(result.get(0).getBranches()).hasSize(1);
        List<Branch> branches = result.get(0).getBranches();
        assertEquals(TEST_BRANCH_NAME, branches.get(0).getName());
        assertEquals(TEST_BRANCH_SHA, branches.get(0).getLastCommitSha());
    }
}
