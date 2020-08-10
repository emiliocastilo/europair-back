package com.europair.management.rest.model.fleet.dto;

import com.europair.management.rest.model.audit.dto.AuditModificationBaseDTO;
import com.europair.management.rest.model.audit.entity.AuditModificationBaseEntity;
import com.europair.management.rest.model.fleet.entity.Aircraft;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AircraftBaseDto extends AuditModificationBaseDTO {

    @JsonProperty("id")
    private Long id;

    // ToDo: pendiente entidad/dto
    private Long airport;

    @JsonProperty("aircraft")
    private AircraftDto aircraft;

    @JsonProperty("mainBase")
    private Boolean mainBase;
}
