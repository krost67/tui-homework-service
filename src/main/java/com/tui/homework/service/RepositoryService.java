package com.tui.homework.service;

import com.tui.homework.connector.IRepositoryConnector;
import com.tui.homework.exception.ResourceNotAcceptableException;
import com.tui.homework.exception.ResourceNotFoundException;
import com.tui.homework.model.Branch;
import com.tui.homework.model.Repository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RepositoryService implements IRepositoryService {

    private final IRepositoryConnector repositoryConnector;
    private final IBranchService branchService;

    @Override
    public List<Repository> getRepositories(String username, Integer pageNumber, Integer pageSize) {
        List<Repository> repositories = repositoryConnector.fetchRepositories(username, pageNumber, pageSize);
        setBranchesAsync(repositories);

        return repositories;
    }

    private void setBranchesAsync(List<Repository> repositories) {
        Map<String, CompletableFuture<List<Branch>>> repoNameToFutureBranches = new HashMap<>();
        for (Repository repository : repositories) {
            repoNameToFutureBranches.put(
                    repository.getName(),
                    CompletableFuture.supplyAsync(() -> branchService.getBranches(repository.getOwner(), repository.getName()))
            );
        }

        repositories.forEach(e -> {
            try {
                e.setBranches(repoNameToFutureBranches.get(e.getName()).get());
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            } catch (ExecutionException ex) {
                Throwable t = ex.getCause();
                if (t instanceof ResourceNotFoundException) {
                    throw (ResourceNotFoundException) t;
                } else if (t instanceof ResourceNotAcceptableException) {
                    throw (ResourceNotAcceptableException) t;
                } else {
                    throw new RuntimeException(ex);
                }
            }
        });
    }
}
