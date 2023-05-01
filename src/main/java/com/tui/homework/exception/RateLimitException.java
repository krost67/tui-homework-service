package com.tui.homework.exception;

import java.io.Serial;
import lombok.Getter;

@Getter
public class RateLimitException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -7093146269688658084L;

    public RateLimitException() {
        super("API rate limit exceeded");
    }
}
