package com.europair.management.impl.mappers.airport;

import com.europair.management.api.dto.airport.TerminalDto;
import com.europair.management.impl.mappers.audit.AuditModificationBaseMapperConfig;
import com.europair.management.rest.model.airport.entity.Terminal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingInheritanceStrategy;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(config = AuditModificationBaseMapperConfig.class,
        mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_ALL_FROM_CONFIG)
public interface ITerminalMapper {

    ITerminalMapper INSTANCE = Mappers.getMapper(ITerminalMapper.class);

    //    @Mapping(target = "airport", ignore = true)
    TerminalDto toDto(final Terminal entity);

    Terminal toEntity(final TerminalDto dto);

    @Mapping(target = "airport", ignore = true)
    void updateFromDto(final TerminalDto dto, @MappingTarget Terminal entity);

}
