package com.europair.management.impl.mappers.expedient;

import com.europair.management.api.dto.expedient.ProviderDto;
import com.europair.management.impl.mappers.audit.AuditModificationBaseMapperConfig;
import com.europair.management.rest.model.expedient.entity.Provider;
import org.mapstruct.Mapper;
import org.mapstruct.MappingInheritanceStrategy;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(config = AuditModificationBaseMapperConfig.class,
        mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_ALL_FROM_CONFIG)
public interface IProviderMapper {

    IProviderMapper INSTANCE = Mappers.getMapper(IProviderMapper.class);

    ProviderDto toDto(final Provider entity);

    Provider toEntity(final ProviderDto providerDto);

    void updateFromDto(final ProviderDto providerDto, @MappingTarget Provider provider);

}
