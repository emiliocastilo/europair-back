package com.europair.management.api.dto.common;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class StateChangeDto<T> {

    @NotEmpty
    private List<Long> idList;

    @NotNull
    private T state;

}
