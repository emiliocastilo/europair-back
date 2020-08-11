package com.europair.management.impl.mappers.operators;

import com.europair.management.api.dto.operators.dto.CertificationDTO;
import com.europair.management.rest.model.audit.mapper.AuditModificationBaseMapperConfig;

import com.europair.management.rest.model.operators.entity.Certification;
import org.mapstruct.Mapper;
import org.mapstruct.MappingInheritanceStrategy;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(config = AuditModificationBaseMapperConfig.class, mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_ALL_FROM_CONFIG)
public interface ICertificationMapper {

  ICertificationMapper INSTANCE = Mappers.getMapper(ICertificationMapper.class);

  CertificationDTO toDto (final Certification entity);

  List<CertificationDTO> toListDtos (final List<Certification> listEntities);

  Certification toEntity (final CertificationDTO certificationDTO);

  void updateFromDto(final CertificationDTO certificationDTO, @MappingTarget Certification certification);

}
