package com.tui.homework.connector.github.service.mapper;

import com.tui.homework.connector.github.model.GitHubRepository;
import com.tui.homework.model.Repository;

public interface IGitHubRepositoryMapper {

    Repository toRepository(GitHubRepository gitHubRepository);
}
