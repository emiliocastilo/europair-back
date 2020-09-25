package com.europair.management.impl.mappers.servicetypes;

import com.europair.management.api.dto.services.ServiceDto;
import com.europair.management.impl.mappers.audit.AuditModificationBaseMapperConfig;
import com.europair.management.rest.model.services.entity.Service;
import org.mapstruct.Mapper;
import org.mapstruct.MappingInheritanceStrategy;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(config = AuditModificationBaseMapperConfig.class,
  mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_ALL_FROM_CONFIG)
public interface IServiceTypeMapper {

  IServiceTypeMapper INSTANCE = Mappers.getMapper(IServiceTypeMapper.class);

  ServiceDto toDto(final Service entity);

  Service toEntity(final ServiceDto serviceDto);

  void updateFromDto(final ServiceDto serviceDto, @MappingTarget Service service);

}
