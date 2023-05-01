package com.tui.homework.service;

import static com.tui.homework.TestData.TEST_REPOSITORY_NAME;
import static com.tui.homework.TestData.TEST_USERNAME;
import static com.tui.homework.TestData.prepareBranch;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.tui.homework.connector.IBranchConnector;
import com.tui.homework.model.Branch;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BranchServiceTest {

    @Mock
    private IBranchConnector branchConnector;

    private IBranchService branchService;

    @BeforeEach
    public void setup() {
        branchService = new BranchService(branchConnector);
    }

    @Test
    public void getBranches_ShouldReturnBranches_WhenValidParamsArePassed() {
        // Given
        Branch branch = prepareBranch();
        when(branchConnector.fetchBranches(TEST_USERNAME, TEST_REPOSITORY_NAME))
                .thenReturn(List.of(branch));

        // When
        List<Branch> result = branchService.getBranches(TEST_USERNAME, TEST_REPOSITORY_NAME);

        // Then
        verify(branchConnector, times(1)).fetchBranches(TEST_USERNAME, TEST_REPOSITORY_NAME);
        assertThat(result).hasSize(1).containsExactly(branch);
    }
}
