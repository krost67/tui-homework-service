package com.tui.homework.service;

import com.tui.homework.connector.IBranchConnector;
import com.tui.homework.model.Branch;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BranchService implements IBranchService {

    private final IBranchConnector branchConnector;

    @Override
    public List<Branch> getBranches(String username, String repositoryName) {
        return branchConnector.fetchBranches(username, repositoryName);
    }
}
