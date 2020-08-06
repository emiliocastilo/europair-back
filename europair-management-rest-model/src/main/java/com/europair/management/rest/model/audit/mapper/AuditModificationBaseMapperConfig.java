package com.europair.management.rest.model.audit.mapper;

import com.europair.management.rest.model.audit.dto.AuditModificationBaseDTO;
import com.europair.management.rest.model.audit.entity.AuditModificationBaseEntity;
import org.mapstruct.MapperConfig;
import org.mapstruct.Mapping;

import java.util.List;

@MapperConfig
public interface AuditModificationBaseMapperConfig {

  AuditModificationBaseDTO toDto (final AuditModificationBaseEntity entity);

  List<AuditModificationBaseDTO> toListDtos (final List<AuditModificationBaseEntity> listEntities);

  // Prevent audit fields being mapped and overwrited
  @Mapping(target = "modifiedBy", ignore = true)
  @Mapping(target = "modifiedAt", ignore = true)
  AuditModificationBaseEntity toEntity (final AuditModificationBaseDTO auditModificationBaseDTO);

}
