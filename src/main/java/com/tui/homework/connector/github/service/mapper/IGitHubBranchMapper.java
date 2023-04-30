package com.tui.homework.connector.github.service.mapper;

import com.tui.homework.connector.github.model.GitHubBranch;
import com.tui.homework.model.Branch;

public interface IGitHubBranchMapper {

    Branch toBranch(GitHubBranch gitHubBranch);
}
