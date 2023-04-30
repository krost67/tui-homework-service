package com.tui.homework.connector.github.service.mapper;

import com.tui.homework.connector.github.model.GitHubRepository;
import com.tui.homework.model.Repository;
import java.util.Objects;
import org.springframework.stereotype.Component;

@Component
public class GitHubRepositoryMapper implements IGitHubRepositoryMapper {

    @Override
    public Repository toRepository(GitHubRepository gitHubRepository) {
        Repository repository = new Repository();
        repository.setName(gitHubRepository.getName());

        if (Objects.nonNull(gitHubRepository.getOwner())) {
            repository.setOwner(gitHubRepository.getOwner().getLogin());
        }

        return repository;
    }
}
