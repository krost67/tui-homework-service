package com.tui.homework.exception;

import java.io.Serial;
import lombok.Getter;

@Getter
public class MissedParameterException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 7220382335605887103L;

    private final String parameterName;

    public MissedParameterException(String parameterName) {
        super(String.format("Missed parameter [%s]", parameterName));
        this.parameterName = parameterName;
    }
}
