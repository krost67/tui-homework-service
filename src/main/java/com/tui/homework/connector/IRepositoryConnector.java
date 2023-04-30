package com.tui.homework.connector;

import com.tui.homework.model.Repository;
import java.util.List;

public interface IRepositoryConnector {

    List<Repository> fetchRepositories(String username, Integer pageNumber, Integer pageSize);
}
