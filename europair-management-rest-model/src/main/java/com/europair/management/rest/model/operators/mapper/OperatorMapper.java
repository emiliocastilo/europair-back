package com.europair.management.rest.model.operators.mapper;

import com.europair.management.rest.model.audit.mapper.AuditModificationBaseMapperConfig;
import com.europair.management.rest.model.operators.dto.OperatorDTO;
import com.europair.management.rest.model.operators.entity.Operator;
import org.mapstruct.Mapper;
import org.mapstruct.MappingInheritanceStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(config = AuditModificationBaseMapperConfig.class, mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_ALL_FROM_CONFIG)
public interface OperatorMapper {

  OperatorMapper INSTANCE = Mappers.getMapper(OperatorMapper.class);

  OperatorDTO toDto (final Operator entity);

  List<OperatorDTO> toListDtos (final List<Operator> listEntities);

  Operator toEntity (final OperatorDTO operadorDTO);


}
