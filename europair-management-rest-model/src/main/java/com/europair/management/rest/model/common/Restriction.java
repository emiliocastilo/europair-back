package com.europair.management.rest.model.common;

import lombok.Builder;
import lombok.Data;

import javax.persistence.criteria.Predicate;

@Data
@Builder
public class Restriction {

    private String column;
    private String value;
    private OperatorEnum operator;
    private Predicate.BooleanOperator queryOperator;
}
