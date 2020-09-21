package com.europair.management.impl.mappers.audit;

import com.europair.management.api.dto.audit.AuditModificationBaseDTO;
import com.europair.management.rest.model.audit.entity.AuditModificationBaseEntity;
import org.mapstruct.MapperConfig;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@MapperConfig(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AuditModificationBaseMapperConfig {

  AuditModificationBaseDTO toDto (final AuditModificationBaseEntity entity);

  List<AuditModificationBaseDTO> toListDtos (final List<AuditModificationBaseEntity> listEntities);

  // Prevent audit fields being mapped and overwrited
  @Mapping(target = "createdBy", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "modifiedBy", ignore = true)
  @Mapping(target = "modifiedAt", ignore = true)
  AuditModificationBaseEntity toEntity (final AuditModificationBaseDTO auditModificationBaseDTO);

  /**
   * NOTE:
   * to prevent null behaviour and evict to update with null values we can use for example:
   * * Prevent null value in one property:
   * @Mapping(target = "status", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
   *
   * * Activate prevent null value strategy for all the mapper for example:
   *
   * @Mapper(config = AuditModificationBaseMapperConfig.class,
   *   mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_ALL_FROM_CONFIG, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
   *
   *   and if we need to activate the behaviour related for a null property especifically we can use on a method
   *
   *     @Mapping(target = "statusId", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
   *   void updateFromDto(final FileDTO fileDTO, @MappingTarget File file);
   */

}
