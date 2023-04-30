package com.tui.homework.exception;

import java.io.Serial;
import lombok.Getter;

@Getter
public class ResourceNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -643306018081495214L;

    private String resourceName;

    public ResourceNotFoundException() {
        super("Resource is not found");
    }

    public ResourceNotFoundException(String resourceName) {
        super(String.format("Resource [%s] is not found", resourceName));
        this.resourceName = resourceName;
    }
}
