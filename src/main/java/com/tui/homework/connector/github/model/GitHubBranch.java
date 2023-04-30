package com.tui.homework.connector.github.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GitHubBranch {

    private String name;
    private GitHubCommit commit;
}
