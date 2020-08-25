package com.europair.management.impl.mappers.expedient;

import com.europair.management.api.dto.expedient.ClientDto;
import com.europair.management.impl.mappers.audit.AuditModificationBaseMapperConfig;
import com.europair.management.rest.model.expedient.entity.Client;
import org.mapstruct.Mapper;
import org.mapstruct.MappingInheritanceStrategy;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(config = AuditModificationBaseMapperConfig.class,
        mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_ALL_FROM_CONFIG)
public interface IClientMapper {

    IClientMapper INSTANCE = Mappers.getMapper(IClientMapper.class);

    ClientDto toDto(final Client entity);

    Client toEntity(final ClientDto clientDto);

    void updateFromDto(final ClientDto clientDto, @MappingTarget Client client);

}
