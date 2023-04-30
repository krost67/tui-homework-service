package com.tui.homework.model;

import java.io.Serial;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Branch implements Serializable {

    @Serial
    private static final long serialVersionUID = -8418831148031003526L;

    private String name;
    private String lastCommitSha;
}
