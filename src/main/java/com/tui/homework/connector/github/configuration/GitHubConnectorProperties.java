package com.tui.homework.connector.github.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "homework.connector.github")
public class GitHubConnectorProperties {

    private String host;
    private String reposUri;
    private String branchesUri;
}
