package com.tui.homework.connector.github.mapper;

import static com.tui.homework.TestData.TEST_REPOSITORY_NAME;
import static com.tui.homework.TestData.prepareGitHubRepository;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.tui.homework.connector.github.model.GitHubRepository;
import com.tui.homework.connector.github.service.mapper.GitHubRepositoryMapper;
import com.tui.homework.connector.github.service.mapper.IGitHubRepositoryMapper;
import com.tui.homework.model.Repository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class GitHubBranchMapperTest {

    private IGitHubRepositoryMapper repositoryMapper;

    @BeforeEach
    public void setup() {
        repositoryMapper = new GitHubRepositoryMapper();
    }

    @Test
    public void toRepository_ShouldConvertGitRepository_WhenExecuted() {
        // Given
        GitHubRepository gitHubRepository = prepareGitHubRepository(TEST_REPOSITORY_NAME, false);

        // When
        Repository repository = repositoryMapper.toRepository(gitHubRepository);

        // Then
        assertNotNull(repository);
        assertEquals(gitHubRepository.getName(), repository.getName());
        assertEquals(gitHubRepository.getOwner().getLogin(), repository.getOwner());
    }
}
