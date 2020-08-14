package com.europair.management.impl.mappers.operators;

import com.europair.management.api.dto.operators.OperatorDTO;


import com.europair.management.impl.mappers.audit.AuditModificationBaseMapperConfig;
import com.europair.management.rest.model.operators.entity.Operator;
import org.mapstruct.Mapper;
import org.mapstruct.MappingInheritanceStrategy;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(config = AuditModificationBaseMapperConfig.class,
        mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_ALL_FROM_CONFIG,
        uses = ICertificationMapper.class)
public interface IOperatorMapper {

  IOperatorMapper INSTANCE = Mappers.getMapper(IOperatorMapper.class);

  OperatorDTO toDto (final Operator entity);

  List<OperatorDTO> toListDtos (final List<Operator> listEntities);

  Operator toEntity (final OperatorDTO operadorDTO);

  void updateFromDto(final OperatorDTO operadorDTO, @MappingTarget Operator operator);


}