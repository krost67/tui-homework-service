package com.tui.homework;

import com.tui.homework.connector.github.model.GitHubBranch;
import com.tui.homework.connector.github.model.GitHubCommit;
import com.tui.homework.connector.github.model.GitHubOwner;
import com.tui.homework.connector.github.model.GitHubRepository;
import com.tui.homework.model.Branch;
import com.tui.homework.model.Repository;

public final class TestData {

    public static final String TEST_USERNAME = "username";

    public static final String TEST_REPOSITORY_NAME = "repository";
    public static final String TEST_FORK_REPOSITORY_NAME = "fork-repository";

    public static final String TEST_BRANCH_NAME = "branch";
    public static final String TEST_BRANCH_SHA = "sha";

    public static final String TEST_GITHUB_HOST = "https://api.test.com";
    public static final String TEST_GITHUB_BRANCHES_URI = "/repos/{username}/{repo}/branches";
    public static final String TEST_GITHUB_REPOS_URI = "/users/{username}/repos";

    public static Branch prepareBranch() {
        return new Branch(TEST_BRANCH_NAME, TEST_BRANCH_SHA);
    }

    public static Repository prepareRepository() {
        Repository repo =  new Repository();
        repo.setName(TEST_REPOSITORY_NAME);
        repo.setOwner(TEST_USERNAME);

        return repo;
    }

    public static GitHubBranch prepareGitHubBranch() {
        GitHubBranch branch = new GitHubBranch();
        branch.setName(TEST_BRANCH_NAME);
        branch.setCommit(new GitHubCommit(TEST_BRANCH_SHA));

        return branch;
    }

    public static GitHubRepository prepareGitHubRepository(String name, boolean isFork) {
        GitHubRepository repository = new GitHubRepository();
        repository.setName(name);
        repository.setOwner(new GitHubOwner(1L, TEST_USERNAME));
        repository.setFork(isFork);

        return repository;
    }
}
