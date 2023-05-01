package com.tui.homework.connector.github.mapper;

import static com.tui.homework.TestData.prepareGitHubBranch;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.tui.homework.connector.github.model.GitHubBranch;
import com.tui.homework.connector.github.service.mapper.GitHubBranchMapper;
import com.tui.homework.connector.github.service.mapper.IGitHubBranchMapper;
import com.tui.homework.model.Branch;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class GitHubRepositoryMapperTest {

    private IGitHubBranchMapper branchMapper;

    @BeforeEach
    public void setup() {
        branchMapper = new GitHubBranchMapper();
    }

    @Test
    public void toBranch_ShouldConvertGitBranch_WhenExecuted() {
        // Given
        GitHubBranch gitHubBranch = prepareGitHubBranch();

        // When
        Branch branch = branchMapper.toBranch(gitHubBranch);

        // Then
        assertNotNull(branch);
        assertEquals(gitHubBranch.getName(), branch.getName());
        assertEquals(gitHubBranch.getCommit().getSha(), branch.getLastCommitSha());
    }
}
