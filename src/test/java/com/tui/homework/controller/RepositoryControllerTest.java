package com.tui.homework.controller;

import static com.tui.homework.TestData.TEST_USERNAME;
import static com.tui.homework.TestData.prepareRepository;
import static com.tui.homework.connector.github.utils.GitHubParameters.USERNAME_PARAM;
import static com.tui.homework.exception.handler.HomeworkExceptionHandler.MISSING_PARAMETER_MESSAGE;
import static com.tui.homework.exception.handler.HomeworkExceptionHandler.RESOURCE_NOT_FOUND_MESSAGE;
import static com.tui.homework.exception.handler.HomeworkExceptionHandler.UNEXPECTED_ERROR_MESSAGE;
import static com.tui.homework.model.ErrorType.MISSING_PARAMETER;
import static com.tui.homework.model.ErrorType.NOT_FOUND;
import static com.tui.homework.model.ErrorType.SERVICE_UNAVAILABLE;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tui.homework.exception.ResourceNotFoundException;
import com.tui.homework.model.ErrorMessage;
import com.tui.homework.model.PagedResult;
import com.tui.homework.model.Repository;
import com.tui.homework.service.RepositoryService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = RepositoryController.class)
public class RepositoryControllerTest {

    private static final String GET_REPOSITORIES_V1 = "/api/v1/repositories";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RepositoryService repositoryService;

    @Test
    void getRepositories_ShouldReturnOK_WhenExecuted() throws Exception {
        // Given
        Repository repository = prepareRepository();
        when(repositoryService.getRepositories(anyString(), anyInt(), anyInt()))
                .thenReturn(List.of(repository));

        // When
        mvc.perform(MockMvcRequestBuilders.get(GET_REPOSITORIES_V1)
                            .contentType(MediaType.APPLICATION_JSON)
                            .queryParam(USERNAME_PARAM, TEST_USERNAME)
                            .queryParam("pageNumber", "2")
                            .queryParam("pageSize", "10"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(
                        new PagedResult<>(List.of(repository), 2, 10)))
                );
    }

    @Test
    void getRepositories_shouldSetDefaultParameters_WhenTheyAreNotPassed() throws Exception {
        // Given
        Repository repository = prepareRepository();
        when(repositoryService.getRepositories(anyString(), anyInt(), anyInt()))
                .thenReturn(List.of(repository));
        ArgumentCaptor<Integer> page = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Integer> pageSize = ArgumentCaptor.forClass(Integer.class);

        // When
        mvc.perform(MockMvcRequestBuilders.get(GET_REPOSITORIES_V1)
                            .contentType(MediaType.APPLICATION_JSON)
                            .queryParam(USERNAME_PARAM, TEST_USERNAME))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(
                        new PagedResult<>(List.of(repository), 1, 30)))
                );

        // Then
        verify(repositoryService, times(1)).getRepositories(
                eq(TEST_USERNAME), page.capture(), pageSize.capture()
        );
        assertThat(page.getValue()).isEqualTo(1);
        assertThat(pageSize.getValue()).isEqualTo(30);
    }

    @Test
    void getRepositories_shouldReturnBadRequest_WhenUsernameIsNotPassed() throws Exception {
        // When
        mvc.perform(MockMvcRequestBuilders.get(GET_REPOSITORIES_V1)
                            .contentType(MediaType.APPLICATION_JSON)
                            .queryParam(USERNAME_PARAM, ""))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(objectMapper.writeValueAsString(
                        new ErrorMessage(MISSING_PARAMETER, String.format(MISSING_PARAMETER_MESSAGE, "username"))))
                );
    }

    @Test
    void getRepositories_shouldReturnServiceUnavailable_WhenRuntimeExceptionCaught() throws Exception {
        // Given
        when(repositoryService.getRepositories(anyString(), anyInt(), anyInt()))
                .thenThrow(RuntimeException.class);

        // When
        mvc.perform(MockMvcRequestBuilders.get(GET_REPOSITORIES_V1)
                            .contentType(MediaType.APPLICATION_JSON)
                            .queryParam(USERNAME_PARAM, TEST_USERNAME))
                .andExpect(status().isServiceUnavailable())
                .andExpect(content().json(objectMapper.writeValueAsString(
                        new ErrorMessage(SERVICE_UNAVAILABLE, UNEXPECTED_ERROR_MESSAGE)))
                );
    }

    @Test
    void getRepositories_shouldReturnNotFound_WhenResourceNotFoundExceptionCaught() throws Exception {
        // Given
        when(repositoryService.getRepositories(anyString(), anyInt(), anyInt()))
                .thenThrow(ResourceNotFoundException.class);

        // When
        mvc.perform(MockMvcRequestBuilders.get(GET_REPOSITORIES_V1)
                            .contentType(MediaType.APPLICATION_JSON)
                            .queryParam(USERNAME_PARAM, TEST_USERNAME))
                .andExpect(status().isNotFound())
                .andExpect(content().json(objectMapper.writeValueAsString(
                        new ErrorMessage(NOT_FOUND, RESOURCE_NOT_FOUND_MESSAGE)))
                );
    }
}
