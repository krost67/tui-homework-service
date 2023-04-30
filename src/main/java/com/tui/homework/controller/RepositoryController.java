package com.tui.homework.controller;

import com.tui.homework.model.PagedResult;
import com.tui.homework.model.Repository;
import com.tui.homework.exception.MissedParameterException;
import com.tui.homework.service.IRepositoryService;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/repositories")
@RequiredArgsConstructor
public class RepositoryController {

    private static final Integer DEFAULT_PAGE_NUMBER = 1;
    private static final Integer DEFAULT_PAGE_SIZE = 30;
    private static final Integer MAX_PAGE_SIZE = 100;
    private static final String USERNAME_PARAMETER = "username";

    private final IRepositoryService repositoryService;

    @GetMapping
    public PagedResult<Repository> getRepositories(@RequestParam(required = false) String username,
                                                   @RequestParam(required = false) Integer pageNumber,
                                                   @RequestParam(required = false) Integer pageSize) {
        if (StringUtils.isBlank(username)) {
            throw new MissedParameterException(USERNAME_PARAMETER);
        }

        if (Objects.isNull(pageNumber) || pageNumber < 1) {
            pageNumber = DEFAULT_PAGE_NUMBER;
        }

        if (Objects.isNull(pageSize) || pageSize < 1 || pageSize > MAX_PAGE_SIZE) {
            pageSize = DEFAULT_PAGE_SIZE;
        }

        List<Repository> repositories = repositoryService.getRepositories(username, pageNumber, pageSize);
        return new PagedResult<>(repositories, pageNumber, pageSize);
    }
}
