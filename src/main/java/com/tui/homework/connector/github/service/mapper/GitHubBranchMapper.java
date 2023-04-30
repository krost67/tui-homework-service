package com.tui.homework.connector.github.service.mapper;

import com.tui.homework.connector.github.model.GitHubBranch;
import com.tui.homework.model.Branch;
import java.util.Objects;
import org.springframework.stereotype.Component;

@Component
public class GitHubBranchMapper implements IGitHubBranchMapper {

    @Override
    public Branch toBranch(GitHubBranch gitHubBranch) {
        Branch branch = new Branch();
        branch.setName(gitHubBranch.getName());

        if (Objects.nonNull(gitHubBranch.getCommit())) {
            branch.setLastCommitSha(gitHubBranch.getCommit().getSha());
        }

        return branch;
    }
}
