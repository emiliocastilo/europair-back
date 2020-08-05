package com.europair.management.rest.model.common;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class CoreCriteria {
    List<Restriction> restrictions;
}
