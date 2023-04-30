package com.tui.homework.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ErrorType {

    SERVICE_UNAVAILABLE("service_unavailable"),
    MISSING_PARAMETER("missing_parameter"),
    NOT_ACCEPTABLE_MEDIA_TYPE("not_acceptable_media_type"),
    NOT_ACCEPTABLE_RESOURCE("not_acceptable_resource"),
    NOT_FOUND("resource_not_found");

    private final String value;

    ErrorType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return value;
    }
}
