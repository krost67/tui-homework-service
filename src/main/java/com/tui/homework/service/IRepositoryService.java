package com.tui.homework.service;

import com.tui.homework.model.Repository;
import java.util.List;

public interface IRepositoryService {

    List<Repository> getRepositories(String username, Integer page, Integer pageSize);
}
