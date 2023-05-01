package com.tui.homework.exception;

import java.io.Serial;
import lombok.Getter;

@Getter
public class ResourceNotAvailableException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -5924804007067823458L;

    private String resourceName;

    public ResourceNotAvailableException() {
        super("Resource is not available");
    }

    public ResourceNotAvailableException(String resourceName) {
        super(String.format("Resource [%s] is not available", resourceName));
        this.resourceName = resourceName;
    }
}
