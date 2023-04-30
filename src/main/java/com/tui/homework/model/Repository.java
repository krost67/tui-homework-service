package com.tui.homework.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Repository implements Serializable {

    @Serial
    private static final long serialVersionUID = -1538684183466754771L;

    private String name;
    private String owner;
    private List<Branch> branches;
}
