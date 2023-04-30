package com.tui.homework.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagedResult<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = -3801612221294530671L;

    private List<T> items;
    private int pageNumber;
    private int pageSize;
}
