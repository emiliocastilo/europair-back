package com.europair.management.rest.model.operators.mapper;

import com.europair.management.rest.model.audit.mapper.AuditModificationBaseMapperConfig;
import com.europair.management.rest.model.operators.dto.CertificationDTO;
import com.europair.management.rest.model.operators.dto.OperatorDTO;
import com.europair.management.rest.model.operators.entity.Certification;
import com.europair.management.rest.model.operators.entity.Operator;
import org.mapstruct.Mapper;
import org.mapstruct.MappingInheritanceStrategy;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(config = AuditModificationBaseMapperConfig.class, mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_ALL_FROM_CONFIG)
public interface CertificationMapper {

  CertificationMapper INSTANCE = Mappers.getMapper(CertificationMapper.class);

  CertificationDTO toDto (final Certification entity);

  List<CertificationDTO> toListDtos (final List<Certification> listEntities);

  Certification toEntity (final CertificationDTO certificationDTO);

  void updateFromDto(final CertificationDTO certificationDTO, @MappingTarget Certification certification);

}
