package com.tui.homework.connector.github.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class GitHubParameters {

    public static final String USERNAME_PARAM = "username";
    public static final String REPO_NAME = "repo";
    public static final String TYPE_PARAM = "type";
    public static final String PER_PAGE_PARAM = "per_page";
    public static final String PAGE_PARAM = "page";

    public static String prepareParameterValue(String parameter) {
        return "{" + parameter + "}";
    }
}
