package com.tui.homework.connector;

import com.tui.homework.model.Branch;
import java.util.List;

public interface IBranchConnector {

    List<Branch> fetchBranches(String username, String repositoryName);
}
