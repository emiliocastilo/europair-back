package com.europair.management.impl.mappers.expedient;

import com.europair.management.api.dto.expedient.ExpedientStatusDto;
import com.europair.management.impl.mappers.audit.AuditModificationBaseMapperConfig;
import com.europair.management.rest.model.expedient.entity.ExpedientStatus;
import org.mapstruct.Mapper;
import org.mapstruct.MappingInheritanceStrategy;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(config = AuditModificationBaseMapperConfig.class,
        mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_ALL_FROM_CONFIG)
public interface IExpedientStatusMapper {

    IExpedientStatusMapper INSTANCE = Mappers.getMapper(IExpedientStatusMapper.class);

    ExpedientStatusDto toDto(final ExpedientStatus entity);

    ExpedientStatus toEntity(final ExpedientStatusDto expedientStatusDto);

    void updateFromDto(final ExpedientStatusDto expedientStatusDto, @MappingTarget ExpedientStatus expedientStatus);

}
