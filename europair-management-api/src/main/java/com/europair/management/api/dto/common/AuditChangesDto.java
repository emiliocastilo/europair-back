package com.europair.management.api.dto.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuditChangesDto {

    private String propertyName;

    private String oldValue;

    private String newValue;

}
