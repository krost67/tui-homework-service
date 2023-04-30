package com.tui.homework.connector.github.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GitHubRepository {

    private Long id;
    private String name;
    private GitHubOwner owner;
    private boolean fork;
}
