package com.tui.homework.exception;

import java.io.Serial;
import java.io.Serializable;
import lombok.Getter;

@Getter
public class ResourceNotAcceptableException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 944067498157545366L;

    private String resourceName;

    public ResourceNotAcceptableException() {
        super("Resource is not acceptable");
    }

    public ResourceNotAcceptableException(String resourceName) {
        super(String.format("Resource [%s] is not acceptable", resourceName));
        this.resourceName = resourceName;
    }
}
