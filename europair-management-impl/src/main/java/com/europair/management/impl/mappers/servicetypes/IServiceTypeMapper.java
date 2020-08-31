package com.europair.management.impl.mappers.servicetypes;

import com.europair.management.api.dto.servicetypes.ServiceTypeDTO;
import com.europair.management.impl.mappers.audit.AuditModificationBaseMapperConfig;
import com.europair.management.rest.model.servicetypes.entity.ServiceType;
import org.mapstruct.Mapper;
import org.mapstruct.MappingInheritanceStrategy;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(config = AuditModificationBaseMapperConfig.class,
  mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_ALL_FROM_CONFIG)
public interface IServiceTypeMapper {

  IServiceTypeMapper INSTANCE = Mappers.getMapper(IServiceTypeMapper.class);

  ServiceTypeDTO toDto(final ServiceType entity);

  ServiceType toEntity(final ServiceTypeDTO serviceTypeDTO);

  void updateFromDto(final ServiceTypeDTO serviceTypeDTO, @MappingTarget ServiceType serviceType);

}
