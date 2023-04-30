package com.tui.homework.model;

import java.io.Serial;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorMessage implements Serializable {

    @Serial
    private static final long serialVersionUID = -7865174039179978715L;

    private ErrorType code;
    private String message;
}
