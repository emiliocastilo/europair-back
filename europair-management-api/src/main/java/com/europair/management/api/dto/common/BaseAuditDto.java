package com.europair.management.api.dto.common;

import com.europair.management.api.enums.RevTypeEnum;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class BaseAuditDto {

    private String user;

    private LocalDateTime datetime;

    private RevTypeEnum revType;

    private List<AuditChangesDto> changes;

}
