package com.europair.management.rest.model.common;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Restriction {

    private String column;
    private String value;
    private OperatorEnum operator;
}
