package com.tui.homework.integration;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.cloud.contract.spec.internal.MediaTypes.APPLICATION_JSON;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

import com.tui.homework.model.Branch;
import com.tui.homework.model.PagedResult;
import com.tui.homework.model.Repository;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class GetUserRepositoriesITest extends BaseIntegrationTest {

    private static final String GET_REPOSITORIES_V1_URL = "/api/v1/repositories?username=krost67";

    @Test
    void getRepositories_ShouldReturnOK_WhenExecuted() {
        // Given
        stubFor(get(urlEqualTo(GET_GITHUB_REPOS_URL))
                        .willReturn(aResponse().withHeader(CONTENT_TYPE, APPLICATION_JSON)
                                            .withBodyFile("gitHub_get_repositories_response.json")));
        stubFor(get(urlEqualTo(GET_GITHUB_BRANCHES_URL))
                        .willReturn(aResponse().withHeader(CONTENT_TYPE, APPLICATION_JSON)
                                            .withBodyFile("gitHub_get_branches_response.json")));

        // When
        ResponseEntity<PagedResult<Repository>> response = performGet(GET_REPOSITORIES_V1_URL,
                                                                      new ParameterizedTypeReference<>() {
                                                                      });
        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(Objects.requireNonNull(response.getBody()).getItems())
                .containsExactly(prepareRepositoryITestResult());
    }

    private Repository prepareRepositoryITestResult() {
        Repository repo = new Repository();
        repo.setName("tui-homework-service");
        repo.setOwner("krost67");
        repo.setBranches(List.of(new Branch("main",
                                            "52b3e4c6151c655386aadf5a18c7988dcf5eacfa"))
        );

        return repo;
    }
}
