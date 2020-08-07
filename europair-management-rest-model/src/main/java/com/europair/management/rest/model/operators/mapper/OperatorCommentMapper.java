package com.europair.management.rest.model.operators.mapper;

import com.europair.management.rest.model.audit.mapper.AuditModificationBaseMapperConfig;
import com.europair.management.rest.model.operators.dto.CertificationDTO;
import com.europair.management.rest.model.operators.dto.OperatorCommentDTO;
import com.europair.management.rest.model.operators.dto.OperatorDTO;
import com.europair.management.rest.model.operators.entity.Certification;
import com.europair.management.rest.model.operators.entity.Operator;
import com.europair.management.rest.model.operators.entity.OperatorComment;
import org.mapstruct.Mapper;
import org.mapstruct.MappingInheritanceStrategy;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(config = AuditModificationBaseMapperConfig.class, mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_ALL_FROM_CONFIG)
public interface OperatorCommentMapper {

  OperatorCommentMapper INSTANCE = Mappers.getMapper(OperatorCommentMapper.class);

  OperatorCommentDTO toDto (final OperatorComment entity);

  List<OperatorCommentDTO> toListDtos (final List<OperatorComment> listEntities);

  OperatorComment toEntity (final OperatorCommentDTO operatorCommentDTO);

  void updateFromDto(final OperatorCommentDTO operatorCommentDTO, @MappingTarget OperatorComment operatorComment);

}
