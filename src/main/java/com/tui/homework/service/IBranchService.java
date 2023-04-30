package com.tui.homework.service;

import com.tui.homework.model.Branch;
import java.util.List;

public interface IBranchService {

    List<Branch> getBranches(String username, String repositoryName);
}
